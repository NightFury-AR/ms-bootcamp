package org.nightfury.server_stream.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.nightfury.proto.server_stream.gpslocator.v1.GPSLocatorServiceGrpc;
import org.nightfury.proto.server_stream.gpslocator.v1.LocationRequest;

public class GPSLocatorGRPCClient {
    public static void main(String[] args) {
        // create a channel to the server
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext() // No SSL
                .build();

        // create a stub for the GPSLocatorService
        GPSLocatorServiceGrpc.GPSLocatorServiceStub gpsLocatorServiceStub = GPSLocatorServiceGrpc.newStub(channel);

        // Create a request for the vehicle location
        LocationRequest locationRequest = LocationRequest.newBuilder()
                .setVehicleId(1)
                .setVehicleName("Vehicle 1")
                .build();

        // Use the stub to call the getVehicleLocation method
        gpsLocatorServiceStub.getVehicleLocation(locationRequest , new GPSLocationObserver());

        // Keep the client running to receive streaming responses
        try {
            Thread.sleep(10000); // Sleep for 10 seconds to allow time for responses
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Shutdown the channel
            channel.shutdown();
        }
    }
}
