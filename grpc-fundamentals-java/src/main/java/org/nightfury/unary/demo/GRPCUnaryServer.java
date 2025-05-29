package org.nightfury.unary.demo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.nightfury.unary.service.GPSRegistrationService;

import java.io.IOException;

public class GRPCUnaryServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(9090)
                .addService(new GPSRegistrationService())
                .build();
        System.out.printf("=== GPS REGISTRATION SERVICE === ");
        System.out.println("Starting server on port 9090...");
        server.start();
        System.out.println("Server started!");

        server.awaitTermination();
    }


}

