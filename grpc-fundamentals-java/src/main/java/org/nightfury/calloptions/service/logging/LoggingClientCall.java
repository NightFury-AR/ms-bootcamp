package org.nightfury.calloptions.service.logging;

import io.grpc.ClientCall;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

public class LoggingClientCall<ReqT, RespT> extends ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT> {

    private final MethodDescriptor<ReqT, RespT> method;

    public LoggingClientCall(ClientCall<ReqT, RespT> delegate, MethodDescriptor<ReqT, RespT> method) {
        super(delegate);
        this.method = method;
    }

    @Override
    public void start(Listener<RespT> responseListener, Metadata headers) {
        System.out.println("➡️  Calling method: " + method.getFullMethodName());
        Listener<RespT> loggingListener = new LoggingClientCallListener<>(responseListener);
        super.start(loggingListener, headers);
    }

    @Override
    public void sendMessage(ReqT message) {
        System.out.println("➡️  Sending request: " + message);
        super.sendMessage(message);
    }
}

