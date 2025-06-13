package io.github.nightfuryar.springboot_grpc_demo.service;

import io.github.nightfuryar.springboot_grpc_demo.entity.VehicleEntity;
import io.github.nightfuryar.springboot_grpc_demo.repository.VehicleLocationRepository;
import io.github.nightfuryar.springboot_grpc_demo.repository.VehicleRepository;
import io.github.nightfuryar.springboot_grpc_demo.util.EntityMapper;
import io.github.nightfuryar.springboot_grpc_demo.util.LocationTrackerValidator;
import io.github.nightfuryar.springboot_grpc_demo.util.VehicleLiveLocationObserver;
import io.github.nightfuryar.springboot_grpc_demo.util.VehicleLocationObserver;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.*;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
public class LocationTrackerService extends LocationTrackerServiceGrpc.LocationTrackerServiceImplBase {

    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private VehicleLocationRepository vehicleLocationRepository;
    @Autowired private EntityMapper entityMapper;
    @Autowired private LocationTrackerValidator locationTrackerValidator;

    @Override
    public void registerVehicle(VehicleRegistrationRequest request, StreamObserver<VehicleRegistrationResponse> responseObserver) {
        try {
            Optional<Status> status = locationTrackerValidator.validateVehicleRegistrationRequest(request);
            if (status.isPresent()) {
                throw new StatusException(status.get());
            }
            VehicleEntity savedVehicle = vehicleRepository.save(entityMapper.toVehicleEntity(request));
            if (savedVehicle == null) {
                responseObserver.onError(Status.INTERNAL.withDescription("Failed to save vehicle.").asRuntimeException());
                return;
            }
            responseObserver.onNext(VehicleRegistrationResponse.newBuilder()
                    .setVehicleNumber(savedVehicle.getVehicleNumber())
                    .setStatus("Registered owner: " + savedVehicle.getOwnerName())
                    .build());
            responseObserver.onCompleted();
        } catch (StatusException sE) {
            responseObserver.onError(sE);
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription("UnExpected Error"+e.getMessage()).asRuntimeException());
        }
    }

    //client streaming
    @Override
    public StreamObserver<VehicleLocation> uploadVehicleLocations(StreamObserver<LocationUploadStatus> responseObserver) {
        return new VehicleLocationObserver(responseObserver,vehicleRepository, vehicleLocationRepository);
    }

    // Server Streaming
    @Override
    public void getVehicleLocationHistory(VehicleLocationHistoryRequest request,StreamObserver<VehicleLocation> responseObserver)  {
        String vehicleNumber = request.getVehicleNumber();
        System.out.println(" Received request for vehicle location history: " + vehicleNumber);
        System.out.println(" Vehicle Location History Request: " + request);
        Optional<VehicleEntity> byVehicleNumber = vehicleRepository.findByVehicleNumber(vehicleNumber);
        if (byVehicleNumber.isPresent()) {
            vehicleLocationRepository.findByVehicleVehicleNumber(byVehicleNumber.get().getVehicleNumber())
                    .forEach(vehicleLocation -> {
                        VehicleLocation location = VehicleLocation.newBuilder()
                                        .setVehicleNumber(vehicleNumber)
                                .setLongitude(vehicleLocation.getLongitude())
                                .setLatitude(vehicleLocation.getLatitude())
                                .build();
                        responseObserver.onNext(location);
                    });
        }


        // Dummy example: send 5 location updates with dummy data
        /*for (int i = 0; i < 5; i++) {
            VehicleLocation location = VehicleLocation.newBuilder()
                    .setVehicleNumber(vehicleId)
                    .setLatitude(12.9716 + i * 0.001)  // sample latitudes
                    .setLongitude(77.5946 + i * 0.001) // sample longitudes
                    .setTimestamp(System.currentTimeMillis() + i * 1000)
                    .build();

            responseObserver.onNext(location);

            try {
                Thread.sleep(1000); // Simulate delay for each location update
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }*/

        responseObserver.onCompleted();
    }



    // Bi-directional Streaming
    @Override
    public StreamObserver<VehicleLocation> trackVehicleLocations(StreamObserver<LocationAcknowledgement> responseObserver) {
        return new VehicleLiveLocationObserver(responseObserver);
    }


}
