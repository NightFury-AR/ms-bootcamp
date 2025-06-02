package io.github.nightfuryar.springboot_grpc_demo.client.unary;

import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationTrackerServiceGrpc;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleRegistrationRequest;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleRegistrationResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Order(1) // Ensures this client runs first
@Slf4j
public class VehicleRegistrationClient implements CommandLineRunner {

    private final LocationTrackerServiceGrpc.LocationTrackerServiceBlockingStub blockingStub;

    @Override
    public void run(String... args) {

        VehicleRegistrationRequest request = VehicleRegistrationRequest.newBuilder()
                .setVehicleId("V-001")
                .setOwnerName("Alice")
                .build();
        log.info("1.Unary call ::: start");
        VehicleRegistrationResponse response = blockingStub.registerVehicle(request);
        System.out.println("Response: " + response.getStatus());
        log.info("1.Unary call ::: end");
    }

}
