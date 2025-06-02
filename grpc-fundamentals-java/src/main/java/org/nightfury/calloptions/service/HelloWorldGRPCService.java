package org.nightfury.calloptions.service;

import io.grpc.stub.StreamObserver;
import org.nightfury.proto.common.helloworld.v1.HelloWorldRequest;
import org.nightfury.proto.common.helloworld.v1.HelloWorldResponse;
import org.nightfury.proto.common.helloworld.v1.HelloWorldServiceGrpc;

public class HelloWorldGRPCService extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    @Override
    public void sayHello(HelloWorldRequest request, StreamObserver<HelloWorldResponse> responseObserver) {

        // Extract the name from the request
        String name = request.getName();

        // Create a response message
        HelloWorldResponse response = HelloWorldResponse.newBuilder()
                .setMessage("Hello, " + name + "!")
                .build();


        // Send the response back to the client
        responseObserver.onNext(response);


        // Complete the RPC call
        responseObserver.onCompleted();
    }
}
