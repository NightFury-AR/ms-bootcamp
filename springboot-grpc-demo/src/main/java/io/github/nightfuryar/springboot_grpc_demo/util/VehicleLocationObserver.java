package io.github.nightfuryar.springboot_grpc_demo.util;

import io.github.nightfuryar.springboot_grpc_demo.entity.VehicleEntity;
import io.github.nightfuryar.springboot_grpc_demo.entity.VehicleLocationEntity;
import io.github.nightfuryar.springboot_grpc_demo.repository.VehicleLocationRepository;
import io.github.nightfuryar.springboot_grpc_demo.repository.VehicleRepository;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationUploadStatus;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleLocation;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VehicleLocationObserver implements StreamObserver<VehicleLocation> {

    private final StreamObserver<LocationUploadStatus> responseObserver;
    private final VehicleRepository vehicleRepository;
    private final VehicleLocationRepository vehicleLocationRepository;

    private final ExecutorService executor = Executors.newFixedThreadPool(10); // or use commonPool
    private final List<CompletableFuture<Void>> tasks = new CopyOnWriteArrayList<>();
    private final Queue<VehicleLocation> receivedLocations = new ConcurrentLinkedQueue<>();
    private final EntityMapper entityMapper = new EntityMapper();

    public VehicleLocationObserver(StreamObserver<LocationUploadStatus> responseObserver,
                                   VehicleRepository vehicleRepository,
                                   VehicleLocationRepository vehicleLocationRepository) {
        this.responseObserver = responseObserver;
        this.vehicleRepository = vehicleRepository;
        this.vehicleLocationRepository = vehicleLocationRepository;
    }

    @Override
    public void onNext(VehicleLocation location) {
        CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
            VehicleEntity vehicle = vehicleRepository.findById((long) location.getVehicleId())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found: " + location.getVehicleId()));
            VehicleLocationEntity entity = entityMapper.toLocationEntity(vehicle, location);
            vehicleLocationRepository.save(entity);
            receivedLocations.add(location);
        }, executor);

        tasks.add(task);
    }

    @Override
    public void onError(Throwable t) {
        System.err.println("Stream error: " + t.getMessage());
        responseObserver.onError(t);
    }

    @Override
    public void onCompleted() {
        CompletableFuture
                .allOf(tasks.toArray(new CompletableFuture[0]))
                .whenComplete((v, ex) -> {
                    if (ex != null) {
                        responseObserver.onError(new RuntimeException("Failed to process all locations", ex));
                        return;
                    }

                    LocationUploadStatus status = LocationUploadStatus.newBuilder()
                            .setTotalReceived(receivedLocations.size())
                            .setStatus("Upload successful")
                            .build();

                    responseObserver.onNext(status);
                    responseObserver.onCompleted();

                    executor.shutdown();
                });
    }
}
