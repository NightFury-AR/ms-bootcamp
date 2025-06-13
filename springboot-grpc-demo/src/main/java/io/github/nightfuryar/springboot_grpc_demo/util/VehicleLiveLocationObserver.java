package io.github.nightfuryar.springboot_grpc_demo.util;

import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationAcknowledgement;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleLocation;
import io.grpc.stub.StreamObserver;

public class VehicleLiveLocationObserver implements StreamObserver<VehicleLocation> {

    private final StreamObserver<LocationAcknowledgement> responseObserver;

    public VehicleLiveLocationObserver(StreamObserver<LocationAcknowledgement> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(VehicleLocation location) {
        System.out.printf("Received location update from vehicle %s at lat=%f, long=%f%n",
                location.getVehicleNumber(),
                location.getLatitude(),
                location.getLongitude());

        LocationAcknowledgement ack = LocationAcknowledgement.newBuilder()
                .setMessage("Location received for vehicle: " + location.getVehicleNumber())
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

}
