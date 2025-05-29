package org.nightfury.client_streaming.service;


import io.grpc.stub.StreamObserver;
import org.nightfury.proto.client_streaming.locationemitter.v1.CurrentLocationData;
import org.nightfury.proto.client_streaming.locationemitter.v1.LocationEmitterServiceGrpc;
import org.nightfury.proto.client_streaming.locationemitter.v1.ServerResponse;

public class LocationEmitterService extends LocationEmitterServiceGrpc.LocationEmitterServiceImplBase {

    @Override
    public StreamObserver<CurrentLocationData> sendVehicleLocation(StreamObserver<ServerResponse> responseObserver) {
        return new LocationRequestObserver(responseObserver);
    }
}
