package org.nightfury.bidirectional_streaming.demo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.nightfury.bidirectional_streaming.service.LiveLocationTrackingService;

import java.io.IOException;

public class LiveLocationGRPCServer {
    public static void main(String[] args) throws InterruptedException, IOException {
        Server liveLocationTrackingServer = ServerBuilder
                .forPort(50011)
                .addService(new LiveLocationTrackingService())
                .build();
        liveLocationTrackingServer.start();
        System.out.println("Live Location Tracking Server started on port 50011");
        liveLocationTrackingServer.awaitTermination();
    }
}
