package org.nightfury.server_stream.service;


import io.grpc.stub.StreamObserver;
import org.nightfury.proto.server_stream.gpslocator.v1.GPSLocatorServiceGrpc;
import org.nightfury.proto.server_stream.gpslocator.v1.LocationRequest;
import org.nightfury.proto.server_stream.gpslocator.v1.LocationResponse;


public class GPSLocatorService extends GPSLocatorServiceGrpc.GPSLocatorServiceImplBase {
    @Override
    public void getVehicleLocation(LocationRequest request, StreamObserver<LocationResponse> responseObserver) {
        String vehicleName = request.getVehicleName();
        // Simulate a stream of location updates for the vehicle
        for (int i = 0; i < 5; i++) {
            LocationResponse response = LocationResponse.newBuilder()
                    .setVehicleName(vehicleName)
                    .setLatitude(37.7749 + i * 0.01) // Simulated latitude
                    .setLongitude(-122.4194 + i * 0.01) // Simulated longitude
                    .build();
            responseObserver.onNext(response);
            try {
                Thread.sleep(1000); // Simulate a delay between updates
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Complete the stream
        responseObserver.onCompleted();
    }
}
