package org.nightfury.calloptions.demo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.nightfury.calloptions.service.HelloWorldGRPCService;
import org.nightfury.calloptions.service.ServerTrailingInterceptor;

import java.io.IOException;

public class SimpleGRPCServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // This is where you would set up your gRPC server
        // For example, you might use ServerBuilder to create and start the server
        System.out.println("Starting gRPC server...");

        // Note: Actual server implementation code would go here
        // For example:
        Server server = ServerBuilder
                .forPort(8080)
                .addService(new HelloWorldGRPCService())
                .intercept(new ServerTrailingInterceptor())
                .build()
                .start();

        System.out.println("gRPC server started successfully.");

        server.awaitTermination();
    }
}
