package org.nightfury.bidirectional_streaming.service;

import io.grpc.stub.StreamObserver;
import org.nightfury.proto.bidirectional_streaming.livelocationtracker.v1.ServerCommand;

import java.util.concurrent.atomic.AtomicBoolean;

public class LiveLocationResponseObserver implements StreamObserver<ServerCommand> {

    private final AtomicBoolean completed = new AtomicBoolean(false);

    @Override
    public void onNext(ServerCommand serverCommand) {
        if ("RED".equalsIgnoreCase(serverCommand.getStatus())) {
            System.out.println(" Received RED signal from Server, Stopping ");
            if (!completed.get()) {
                this.onCompleted();
            }
        } else {
            System.out.println(" Received "+serverCommand.getStatus()+" signal");
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(" Error while receiving command from server !!! "+throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        if (!completed.get()) {
            System.out.println(" Location ended ");
            completed.set(true);
        }
    }

    public boolean isCompleted() {
        return completed.get();
    }
}
