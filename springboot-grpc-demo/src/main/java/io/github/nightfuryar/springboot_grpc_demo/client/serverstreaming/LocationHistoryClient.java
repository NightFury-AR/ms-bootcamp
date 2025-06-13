package io.github.nightfuryar.springboot_grpc_demo.client.serverstreaming;


import io.github.nightfuryar.springboot_grpc_demo.client.LocationTrackerClient;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationTrackerServiceGrpc;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleLocationHistoryRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class LocationHistoryClient implements LocationTrackerClient {


    private final LocationTrackerServiceGrpc.LocationTrackerServiceBlockingStub asyncStub;

    @Override
    public void retrieve() {
        VehicleLocationHistoryRequest request = VehicleLocationHistoryRequest.newBuilder()
                .setVehicleNumber("TN78AA0000")
                .build();

        asyncStub.getVehicleLocationHistory(request)
                .forEachRemaining(location -> {
                    System.out.printf("Lat: %.4f, Long: %.4f, Time: %d%n",
                            location.getLatitude(), location.getLongitude(), location.getTimestamp());
                });
    }
}
