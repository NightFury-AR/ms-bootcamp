package org.nightfury.bidirectional_streaming.service;

import io.grpc.stub.StreamObserver;
import org.nightfury.proto.bidirectional_streaming.livelocationtracker.v1.LiveLocationInfo;
import org.nightfury.proto.bidirectional_streaming.livelocationtracker.v1.LiveLocationServiceGrpc;
import org.nightfury.proto.bidirectional_streaming.livelocationtracker.v1.ServerCommand;

public class LiveLocationTrackingService extends LiveLocationServiceGrpc.LiveLocationServiceImplBase {
    @Override
    public StreamObserver<LiveLocationInfo> liveTracking(StreamObserver<ServerCommand> responseObserver) {
        return new LiveLocationRequestObserver(responseObserver);
    }
}
