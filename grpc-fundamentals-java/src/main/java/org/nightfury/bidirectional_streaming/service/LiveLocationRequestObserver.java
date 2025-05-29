package org.nightfury.bidirectional_streaming.service;

import io.grpc.stub.StreamObserver;
import org.nightfury.proto.bidirectional_streaming.livelocationtracker.v1.LiveLocationInfo;
import org.nightfury.proto.bidirectional_streaming.livelocationtracker.v1.ServerCommand;

public class LiveLocationRequestObserver implements StreamObserver<LiveLocationInfo> {

    private final StreamObserver<ServerCommand> responseObserver;
    private boolean isCompleted = false;

    public LiveLocationRequestObserver(StreamObserver<ServerCommand> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(LiveLocationInfo liveLocationInfo) {
        System.out.println("Received location for " + liveLocationInfo.getVehicleName() +
                " with latitude " + liveLocationInfo.getLatitude() +
                ", longitude " + liveLocationInfo.getLongitude() +
                ", destination " + liveLocationInfo.getDestination());

        // Example logic to send commands back based on location
        if (liveLocationInfo.getLongitude() < 0) {
            System.out.println("Reached destination, completing stream.");
            responseObserver.onNext(ServerCommand.newBuilder().setStatus("ARRIVED").build());
            if (!isCompleted) {
                responseObserver.onCompleted();
                isCompleted = true;
            }
            return;
        } else if ("OOTY".equalsIgnoreCase(liveLocationInfo.getDestination())) {
            System.out.println("Destination is OOTY, sending RED signal");
            responseObserver.onNext(ServerCommand.newBuilder().setStatus("RED").build());
        } else {
            System.out.println("Destination is not OOTY, sending GREEN signal");
            responseObserver.onNext(ServerCommand.newBuilder().setStatus("GREEN").build());
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error while receiving live location: " + throwable.getMessage());
        responseObserver.onError(throwable);
    }

    @Override
    public void onCompleted() {
        System.out.println("Live location stream completed by client.");
        if (!isCompleted) {
            responseObserver.onCompleted();
            isCompleted = true;
        }
    }
}
