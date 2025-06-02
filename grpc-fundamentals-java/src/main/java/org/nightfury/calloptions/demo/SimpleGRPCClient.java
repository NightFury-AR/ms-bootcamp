package org.nightfury.calloptions.demo;

import io.grpc.CallOptions;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.nightfury.calloptions.service.logging.ClientLoggingInterceptor;
import org.nightfury.proto.common.helloworld.v1.HelloWorldRequest;
import org.nightfury.proto.common.helloworld.v1.HelloWorldResponse;
import org.nightfury.proto.common.helloworld.v1.HelloWorldServiceGrpc;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleGRPCClient {
    public static void main(String[] args) {

        System.out.println("Starting gRPC client...");
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .overrideAuthority("fake-authority") // This is just an example, in production you would use a real authority
                .build();
        System.out.println("gRPC client started successfully.");

        HelloWorldServiceGrpc.HelloWorldServiceBlockingStub helloWorldServiceBlockingStub = HelloWorldServiceGrpc.newBlockingStub(channel);

        // call options

        // 1. withDeadline - the request must complete before 10 seconds from the current time otherwise DEADLINE_EXCEEDED status will be returned
        helloWorldServiceBlockingStub = helloWorldServiceBlockingStub.withDeadline(Deadline.after(10, TimeUnit.SECONDS));

        // 2. withDeadlineAfter - same as withDeadline but uses a relative time
        helloWorldServiceBlockingStub = helloWorldServiceBlockingStub.withDeadlineAfter(10, TimeUnit.SECONDS);

        // 3. withWaitForReady - Wait for the gRPC channel to become ready before sending the request otherwise it will return UNAVAILABLE status
        helloWorldServiceBlockingStub = helloWorldServiceBlockingStub.withWaitForReady();

        // 4. withMaxMessageSize - Sets max response message size the client accepts otherwise it will return RESOURCE_EXHAUSTED status
        helloWorldServiceBlockingStub = helloWorldServiceBlockingStub.withMaxInboundMessageSize(1024 * 1024); // 1 MB

        // 5. withCompression - Enables gzip compression for outgoing requests
        helloWorldServiceBlockingStub = helloWorldServiceBlockingStub.withCompression("gzip");

        // 6. withCallCredentials
        helloWorldServiceBlockingStub = helloWorldServiceBlockingStub.withCallCredentials(new AccessTokenCallCredentials("Bearer test-token"));

        // 7. withExecutor
        helloWorldServiceBlockingStub = helloWorldServiceBlockingStub.withExecutor(Executors.newFixedThreadPool(4));

        // 8. withInterceptors
        helloWorldServiceBlockingStub = helloWorldServiceBlockingStub.withInterceptors(new ClientLoggingInterceptor());

        // 9. withMinInboundMessageSize
        helloWorldServiceBlockingStub = helloWorldServiceBlockingStub.withMaxOutboundMessageSize(1024); // 1 KB

        // 10. withOptions
        helloWorldServiceBlockingStub = helloWorldServiceBlockingStub.withOption(CallOptions.Key.create("isExperimental"), "true");


        HelloWorldResponse response = helloWorldServiceBlockingStub.sayHello(
                HelloWorldRequest.newBuilder()
                        .setName("kuekuatsu")
                        .build()
        );

        System.out.println("Response Received from Server : "+response.getMessage());
    }
}
