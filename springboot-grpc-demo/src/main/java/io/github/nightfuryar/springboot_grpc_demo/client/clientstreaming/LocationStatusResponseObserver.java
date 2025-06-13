package io.github.nightfuryar.springboot_grpc_demo.client.clientstreaming;

import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationUploadStatus;
import io.grpc.stub.StreamObserver;

public class LocationStatusResponseObserver implements StreamObserver<LocationUploadStatus> {
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
}
