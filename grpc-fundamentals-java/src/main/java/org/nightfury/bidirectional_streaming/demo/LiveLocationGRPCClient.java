package org.nightfury.bidirectional_streaming.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.nightfury.bidirectional_streaming.service.LiveLocationResponseObserver;
import org.nightfury.proto.bidirectional_streaming.livelocationtracker.v1.LiveLocationInfo;
import org.nightfury.proto.bidirectional_streaming.livelocationtracker.v1.LiveLocationServiceGrpc;

public class LiveLocationGRPCClient {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50011)
                .usePlaintext()
                .build();

        LiveLocationServiceGrpc.LiveLocationServiceStub liveLocationServiceStub = LiveLocationServiceGrpc.newStub(channel);
        LiveLocationResponseObserver liveLocationResponseObserver = new LiveLocationResponseObserver();
        StreamObserver<LiveLocationInfo> liveLocationInfoStreamObserver = liveLocationServiceStub.liveTracking(liveLocationResponseObserver);

        for (int i = 12; i > 0; i--) {
            if(liveLocationResponseObserver.isCompleted()){
                System.out.println("Live Location Stream is already end, stopping the additional requests");
                liveLocationInfoStreamObserver.onCompleted();
                break;
            }

            liveLocationInfoStreamObserver.onNext(LiveLocationInfo.newBuilder()
                            .setVehicleId(1)
                    .setVehicleName("Vehicle-1")
                    .setDestination(i < 7 ? "OOTY" : "CHENNAI")
                    .setLatitude(i-2)
                    .setLongitude(i-2)
                    .build());
            Thread.sleep(1000);
        }
        if(!liveLocationResponseObserver.isCompleted()) {
            liveLocationInfoStreamObserver.onCompleted();
        }

        Thread.sleep(20000);
        channel.shutdown();

    }
}
