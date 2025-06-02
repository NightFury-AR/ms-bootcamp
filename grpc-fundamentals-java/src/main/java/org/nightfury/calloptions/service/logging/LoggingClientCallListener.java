package org.nightfury.calloptions.service.logging;

import io.grpc.ForwardingClientCallListener;
import io.grpc.ClientCall;
import io.grpc.Metadata;
import io.grpc.Status;

public class LoggingClientCallListener<RespT> extends ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT> {

    public LoggingClientCallListener(ClientCall.Listener<RespT> delegate) {
        super(delegate);
    }

    @Override
    public void onMessage(RespT message) {
        System.out.println("⬅️  Response received: " + message);
        super.onMessage(message);
    }

    @Override
    public void onClose(Status status, Metadata trailers) {
        System.out.println("🔚 Call ended with status: " + status);
        super.onClose(status, trailers);
    }
}

