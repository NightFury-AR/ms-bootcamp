package org.nightfury.client_streaming.service;

import io.grpc.stub.StreamObserver;
import org.nightfury.proto.client_streaming.locationemitter.v1.ServerResponse;

public class LocationResponseObserver implements StreamObserver<ServerResponse> {

    @Override
    public void onNext(ServerResponse serverResponse) {
        System.out.println("Received Response");
        System.out.println(serverResponse.getStatus());
        System.out.println(serverResponse.getReceivedCoOrdsCount() + " co-ords uploaded !!!");
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(" Error Occurred !!! "+throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.println(" Location data uploaded successfully ");
    }
}
