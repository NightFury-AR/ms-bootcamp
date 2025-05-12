package org.nightfury.unary.demo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.nightfury.unary.service.GreeterService;

import java.io.IOException;

public class GRPCServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(9090)
                .addService(new GreeterService())
                .build();

        System.out.println("Starting server on port 9090...");
        server.start();
        System.out.println("Server started!");

        server.awaitTermination();
    }


}

