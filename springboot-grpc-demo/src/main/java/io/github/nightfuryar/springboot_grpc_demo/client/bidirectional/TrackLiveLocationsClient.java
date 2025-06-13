package io.github.nightfuryar.springboot_grpc_demo.client.bidirectional;

import io.github.nightfuryar.springboot_grpc_demo.client.LocationTrackerClient;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationTrackerServiceGrpc;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleLocation;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class TrackLiveLocationsClient implements LocationTrackerClient {


    private final LocationTrackerServiceGrpc.LocationTrackerServiceStub asyncStub;

    @Override
    public void retrieve() {
        StreamObserver<VehicleLocation> requestObserver = asyncStub.trackVehicleLocations(new LocationAckObserver());

        for (int i = 0; i < 3; i++) {
            VehicleLocation location = VehicleLocation.newBuilder()
                    .setVehicleNumber("TN78AA0000")
                    .setLatitude(11.1 + i)
                    .setLongitude(22.2 + i)
                    .setTimestamp(System.currentTimeMillis())
                    .build();

            requestObserver.onNext(location);
        }

        requestObserver.onCompleted();
    }
}
