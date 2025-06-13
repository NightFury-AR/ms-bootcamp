package io.github.nightfuryar.springboot_grpc_demo.client.bidirectional;

import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationAcknowledgement;
import io.grpc.stub.StreamObserver;

public class LocationAckObserver implements StreamObserver<LocationAcknowledgement> {
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
}
