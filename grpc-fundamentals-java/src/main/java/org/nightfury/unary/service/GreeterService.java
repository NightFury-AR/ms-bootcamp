package org.nightfury.unary.service;


import io.grpc.stub.StreamObserver;
import org.nightfury.proto.greeter.v1.GreetRequest;
import org.nightfury.proto.greeter.v1.GreetResponse;
import org.nightfury.proto.greeter.v1.GreeterServiceGrpc;

public class GreeterService extends GreeterServiceGrpc.GreeterServiceImplBase {

    @Override
    public void greetThem(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        responseObserver.onNext(
                GreetResponse.newBuilder()
                        .setMessage("Hi "+request.getName()+"!!!")
                        .build()
        );

        responseObserver.onCompleted();
        //super.greetThem(request, responseObserver);
    }
}
