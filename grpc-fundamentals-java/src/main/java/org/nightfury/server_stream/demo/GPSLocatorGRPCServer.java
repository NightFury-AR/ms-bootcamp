package org.nightfury.server_stream.demo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.nightfury.server_stream.service.GPSLocatorService;

import java.io.IOException;

public class GPSLocatorGRPCServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server gpsLocatorServer = ServerBuilder.forPort(50051)
                .addService(new GPSLocatorService())
                .build();

        gpsLocatorServer.start();
        System.out.println("Server started on port 50051");

        gpsLocatorServer.awaitTermination();
    }

}
