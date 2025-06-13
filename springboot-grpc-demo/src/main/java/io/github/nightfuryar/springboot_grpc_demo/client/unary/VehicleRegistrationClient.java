package io.github.nightfuryar.springboot_grpc_demo.client.unary;

import io.github.nightfuryar.springboot_grpc_demo.client.LocationTrackerClient;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationTrackerServiceGrpc;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleRegistrationRequest;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleRegistrationResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class VehicleRegistrationClient implements LocationTrackerClient {

    private final LocationTrackerServiceGrpc.LocationTrackerServiceBlockingStub blockingStub;

    @Override
    public void retrieve() {
        VehicleRegistrationRequest request = VehicleRegistrationRequest.newBuilder()
                .setVehicleNumber("TN78AZ0001")
                .setVehicleType("BIKE")
                .setOwnerName("Alice")
                .build();
        VehicleRegistrationResponse response = blockingStub.registerVehicle(request);
        log.info("Vehicle Details Registered Successfully !!! : {}", response.getStatus());
    }
}
