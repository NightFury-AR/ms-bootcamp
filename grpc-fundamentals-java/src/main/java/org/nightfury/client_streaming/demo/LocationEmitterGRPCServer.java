package org.nightfury.client_streaming.demo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.nightfury.client_streaming.service.LocationEmitterService;

import java.io.IOException;

public class LocationEmitterGRPCServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server locationEmitterServer = ServerBuilder
                .forPort(50051)
                .addService(new LocationEmitterService())
                .build();

        locationEmitterServer.start();
        System.out.println("Location Emitter Server started on port 50051");
        locationEmitterServer.awaitTermination();

    }
}
