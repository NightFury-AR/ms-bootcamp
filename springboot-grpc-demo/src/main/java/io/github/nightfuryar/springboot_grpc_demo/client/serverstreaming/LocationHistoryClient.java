package io.github.nightfuryar.springboot_grpc_demo.client.serverstreaming;


import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationTrackerServiceGrpc;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleLocationHistoryRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Order(2) // Ensures this client runs after VehicleRegistrationClient
@Slf4j
public class LocationHistoryClient implements CommandLineRunner {


    private final LocationTrackerServiceGrpc.LocationTrackerServiceBlockingStub blockingStub;

    @Override
    public void run(String... args) {
        VehicleLocationHistoryRequest request = VehicleLocationHistoryRequest.newBuilder()
                .setVehicleId("V-001")
                .build();

        log.info("2.Server Streaming call ::: start");

        blockingStub.getVehicleLocationHistory(request)
                .forEachRemaining(location -> {
                    System.out.printf("Lat: %.4f, Long: %.4f, Time: %d%n",
                            location.getLatitude(), location.getLongitude(), location.getTimestamp());
                });
        log.info("2.Server Streaming call ::: end");
    }
}
