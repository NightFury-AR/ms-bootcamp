package org.nightfury.client_streaming.service;

import io.grpc.stub.StreamObserver;
import org.nightfury.proto.client_streaming.locationemitter.v1.CurrentLocationData;
import org.nightfury.proto.client_streaming.locationemitter.v1.ServerResponse;
import org.nightfury.proto.client_streaming.locationemitter.v1.VehicleLocationData;

import java.util.ArrayList;
import java.util.List;

public class LocationRequestObserver implements StreamObserver<CurrentLocationData> {

    private final StreamObserver<ServerResponse> responseObserver;
    private final List<VehicleLocationData> vehicleLocationData = new ArrayList<>();

    public LocationRequestObserver(StreamObserver<ServerResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(CurrentLocationData locationRequest) {

        // Process the location data (e.g., store it, log it, etc.)
        System.out.println("Received location for vehicle: " + locationRequest.getVehicleName() +
                " at Latitude: " + locationRequest.getLatitude() +
                ", Longitude: " + locationRequest.getLongitude());

        // store them
        VehicleLocationData vehicleLocationData = VehicleLocationData.newBuilder()
                .setVehicleName(locationRequest.getVehicleName())
                .setLatitude(locationRequest.getLatitude())
                .setLongitude(locationRequest.getLongitude())
                .build();

        this.vehicleLocationData.add(vehicleLocationData);

    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error occurred: " + throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.println("Location stream completed.");
        this.responseObserver.onNext(ServerResponse.newBuilder()
                .setStatus("Received Vehicle Details Successfully !!!")
                .addAllReceivedCoOrds(vehicleLocationData)
                .build());
        this.responseObserver.onCompleted();
    }
}
