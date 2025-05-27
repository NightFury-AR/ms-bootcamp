package org.nightfury.server_stream.demo;

import io.grpc.stub.StreamObserver;
import org.night_fury.proto.gps_locator.v1.LocationResponse;

public class GPSLocationObserver implements StreamObserver<LocationResponse> {

    @Override
    public void onNext(LocationResponse value) {
        // Print the location response received from the server
        System.out.println("Received location update: " + value.getVehicleName() +
                " - Latitude: " + value.getLatitude() +
                ", Longitude: " + value.getLongitude());
    }

    @Override
    public void onError(Throwable t) {
        System.err.println("Error occurred: " + t.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.println("Location updates completed.");
    }

}
