package org.nightfury.client_streaming.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.nightfury.client_streaming.service.LocationResponseObserver;
import org.nightfury.proto.client_streaming.locationemitter.v1.CurrentLocationData;
import org.nightfury.proto.client_streaming.locationemitter.v1.LocationEmitterServiceGrpc;


public class LocationEmitterGRPCClient {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext() // No SSL
                .build();

        LocationEmitterServiceGrpc.LocationEmitterServiceStub locationEmitterServiceStub =
                LocationEmitterServiceGrpc.newStub(channel);

        StreamObserver<CurrentLocationData> clientStreamer =
                locationEmitterServiceStub.sendVehicleLocation(new LocationResponseObserver());

        for (int i = 0; i < 3; i++) {
            clientStreamer.onNext(
                    CurrentLocationData.newBuilder()
                            .setVehicleId(100)
                            .setLongitude(99.10+i)
                            .setLatitude(10.1+i)
                            .setVehicleName("Bond")
                            .build()
            );
            Thread.sleep(1000);
        }
        clientStreamer.onCompleted();

        Thread.sleep(10000);
        channel.shutdown();
    }

}
