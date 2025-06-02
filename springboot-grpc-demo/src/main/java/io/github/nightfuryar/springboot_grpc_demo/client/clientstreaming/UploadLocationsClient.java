package io.github.nightfuryar.springboot_grpc_demo.client.clientstreaming;

import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationTrackerServiceGrpc;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationUploadStatus;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleLocation;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Order(3) // Ensures this client runs after VehicleRegistrationClient and LocationHistoryClient
@Slf4j
public class UploadLocationsClient implements CommandLineRunner {


    private final LocationTrackerServiceGrpc.LocationTrackerServiceStub asyncStub;

    @Override
    public void run(String... args) {
        log.info("3.Client Streaming call ::: start");
        StreamObserver<LocationUploadStatus> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(LocationUploadStatus status) {
                System.out.println("Upload Result: " + status.getStatus());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Upload completed");
            }
        };

        StreamObserver<VehicleLocation> requestObserver = asyncStub.uploadVehicleLocations(responseObserver);

        for (int i = 0; i < 3; i++) {
            VehicleLocation location = VehicleLocation.newBuilder()
                    .setVehicleId("V-001")
                    .setLatitude(10.0 + i)
                    .setLongitude(20.0 + i)
                    .setTimestamp(System.currentTimeMillis())
                    .build();

            requestObserver.onNext(location);
        }

        requestObserver.onCompleted();
        log.info("3.Client Streaming call ::: end");
    }
}