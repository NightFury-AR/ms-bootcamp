package io.github.nightfuryar.springboot_grpc_demo.client.clientstreaming;

import io.github.nightfuryar.springboot_grpc_demo.client.LocationTrackerClient;
import io.github.nightfuryar.springboot_grpc_demo.entity.VehicleEntity;
import io.github.nightfuryar.springboot_grpc_demo.repository.VehicleRepository;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationTrackerServiceGrpc;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationUploadStatus;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleLocation;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class UploadLocationsClient implements LocationTrackerClient {

    private final LocationTrackerServiceGrpc.LocationTrackerServiceStub asyncStub;
    private final VehicleRepository vehicleRepository;

    @Override
    public void retrieve() {

        StreamObserver<VehicleLocation> requestObserver = asyncStub.uploadVehicleLocations(new LocationStatusResponseObserver());
        VehicleEntity vehicleEntity = vehicleRepository.findByVehicleNumber("TN78AA0000")
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        for (int i = 0; i < 3; i++) {
            VehicleLocation location = VehicleLocation.newBuilder()
                    .setVehicleNumber(vehicleEntity.getVehicleNumber())
                    .setVehicleId(vehicleEntity.getVehicleId())
                    .setLatitude(10.0 + i)
                    .setLongitude(20.0 + i)
                    .setTimestamp(System.currentTimeMillis())
                    .build();

            requestObserver.onNext(location);
        }

        requestObserver.onCompleted();
    }
}