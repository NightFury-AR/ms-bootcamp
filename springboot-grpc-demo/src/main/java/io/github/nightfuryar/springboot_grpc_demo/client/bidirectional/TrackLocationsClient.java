package io.github.nightfuryar.springboot_grpc_demo.client.bidirectional;

import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationAcknowledgement;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationTrackerServiceGrpc;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleLocation;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Order(4)
@Slf4j
public class TrackLocationsClient implements CommandLineRunner {


    private final LocationTrackerServiceGrpc.LocationTrackerServiceStub asyncStub;

    @Override
    public void run(String... args) {
        log.info("4.Bidirectional Streaming call ::: start");
        StreamObserver<LocationAcknowledgement> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(LocationAcknowledgement ack) {
                System.out.println("Ack: " + ack.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Tracking stream closed by server");
            }
        };

        StreamObserver<VehicleLocation> requestObserver = asyncStub.trackVehicleLocations(responseObserver);

        for (int i = 0; i < 3; i++) {
            VehicleLocation location = VehicleLocation.newBuilder()
                    .setVehicleId("V-001")
                    .setLatitude(11.1 + i)
                    .setLongitude(22.2 + i)
                    .setTimestamp(System.currentTimeMillis())
                    .build();

            requestObserver.onNext(location);
        }

        requestObserver.onCompleted();
        log.info("4.Bidirectional Streaming call ::: end");
    }
}
