package io.github.nightfuryar.springboot_grpc_demo.service;

import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.*;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class LocationTrackerService extends LocationTrackerServiceGrpc.LocationTrackerServiceImplBase {

    @Override
    public void registerVehicle(VehicleRegistrationRequest request, StreamObserver<VehicleRegistrationResponse> responseObserver) {
        String vehicleId = request.getVehicleId();
        String owner = request.getOwnerName();
        // TODO : JPA Call to save vehicle registration details in the database
        VehicleRegistrationResponse response = VehicleRegistrationResponse.newBuilder()
                .setVehicleId(vehicleId)
                .setStatus("Registered owner: " + owner)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // Server Streaming
    @Override
    public void getVehicleLocationHistory(VehicleLocationHistoryRequest request,StreamObserver<VehicleLocation> responseObserver) {
        String vehicleId = request.getVehicleId();

        // Dummy example: send 5 location updates with dummy data
        for (int i = 0; i < 5; i++) {
            VehicleLocation location = VehicleLocation.newBuilder()
                    .setVehicleId(vehicleId)
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
        }

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<VehicleLocation> uploadVehicleLocations(StreamObserver<LocationUploadStatus> responseObserver) {
        return new StreamObserver<>() {
            final List<VehicleLocation> receivedLocations = new ArrayList<>();

            @Override
            public void onNext(VehicleLocation location) {
                receivedLocations.add(location);
                // Here, you could save location to DB or process it
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error receiving locations: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                LocationUploadStatus status = LocationUploadStatus.newBuilder()
                        .setTotalReceived(receivedLocations.size())
                        .setStatus("Upload successful")
                        .build();

                responseObserver.onNext(status);
                responseObserver.onCompleted();
            }
        };
    }

    // Bi-directional Streaming
    @Override
    public StreamObserver<VehicleLocation> trackVehicleLocations(StreamObserver<LocationAcknowledgement> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(VehicleLocation location) {
                System.out.printf("Received location update from vehicle %s at lat=%f, long=%f%n",
                        location.getVehicleId(),
                        location.getLatitude(),
                        location.getLongitude());

                LocationAcknowledgement ack = LocationAcknowledgement.newBuilder()
                        .setMessage("Location received for vehicle: " + location.getVehicleId())
                        .build();

                responseObserver.onNext(ack);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error in tracking stream: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Tracking stream completed.");
                responseObserver.onCompleted();
            }
        };
    }


}
