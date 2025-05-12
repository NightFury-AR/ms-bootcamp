package org.nightfury.unary.demo;




import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.nightfury.proto.greeter.v1.GreetRequest;
import org.nightfury.proto.greeter.v1.GreetResponse;
import org.nightfury.proto.greeter.v1.GreeterServiceGrpc;

public class GRPCClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext() // No SSL
                .build();

        GreeterServiceGrpc.GreeterServiceBlockingStub stub =
                GreeterServiceGrpc.newBlockingStub(channel);


        GreetRequest request = GreetRequest.newBuilder()
                .setName("Bootcamp Learner")
                .build();

        GreetResponse response = stub.greetThem(request);
        System.out.println("Server replied: " + response.getMessage());

        channel.shutdown();
    }
}

