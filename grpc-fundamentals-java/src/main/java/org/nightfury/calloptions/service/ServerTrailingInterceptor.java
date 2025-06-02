package org.nightfury.calloptions.service;

import io.grpc.*;

public class ServerTrailingInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        ServerCall<ReqT, RespT> forwardingCall = new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
            @Override
            public void close(Status status, Metadata trailers) {
                // Add trailing metadata here
                trailers.put(Metadata.Key.of("custom-trailer", Metadata.ASCII_STRING_MARSHALLER), "some-value");
                super.close(status, trailers);
            }
        };
        return next.startCall(forwardingCall, headers);
    }
}

