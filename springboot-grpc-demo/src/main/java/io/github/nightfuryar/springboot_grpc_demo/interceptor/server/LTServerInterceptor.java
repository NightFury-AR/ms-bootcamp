package io.github.nightfuryar.springboot_grpc_demo.interceptor.server;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class LTServerInterceptor implements ServerInterceptor {

    private static final Context.Key<String> AUTH_TOKEN_CTX_KEY = Context.key("authToken");
    private static final Context.Key<String> REQUEST_ID_CTX_KEY = Context.key("requestId");

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> serverCall, Metadata headers, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        // Extract metadata: e.g. Authorization token
        String authToken = headers.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));

        // Auth example (replace with your logic)
        if (!isValidToken(authToken)) {
            serverCall.close(Status.UNAUTHENTICATED.withDescription("Invalid token"), new Metadata());
            return new ServerCall.Listener<ReqT>() {};
        }

        // Add context for tracing/correlation ID
        Context ctx = Context.current()
                .withValue(AUTH_TOKEN_CTX_KEY, authToken)
                .withValue(REQUEST_ID_CTX_KEY, UUID.randomUUID().toString());

        // Wrap call listener with logging
        ServerCall.Listener<ReqT> listener = Contexts.interceptCall(ctx, serverCall, headers, serverCallHandler);

        log.info("Request started: method={}, requestId={}", serverCall.getMethodDescriptor().getFullMethodName(),
                REQUEST_ID_CTX_KEY.get());

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(listener) {
            @Override
            public void onComplete() {
                log.info("Request completed: method={}, requestId={}", serverCall.getMethodDescriptor().getFullMethodName(),
                        REQUEST_ID_CTX_KEY.get());
                super.onComplete();
            }

            @Override
            public void onCancel() {
                log.warn("Request cancelled: method={}, requestId={}", serverCall.getMethodDescriptor().getFullMethodName(),
                        REQUEST_ID_CTX_KEY.get());
                super.onCancel();
            }
        };
    }

    private boolean isValidToken(String token) {
        // TODO: implement real token validation here
        return true;
        //return token != null && token.startsWith("Bearer ");
    }
}
