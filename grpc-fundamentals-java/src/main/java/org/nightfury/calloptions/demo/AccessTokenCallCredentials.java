package org.nightfury.calloptions.demo;
import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;

import java.util.concurrent.Executor;

public class AccessTokenCallCredentials extends CallCredentials {
    private final String token;

    public AccessTokenCallCredentials(String token) {
        this.token = token;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
        appExecutor.execute(() -> {
            try {
                Metadata headers = new Metadata();
                Metadata.Key<String> AUTH_HEADER =
                        Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
                headers.put(AUTH_HEADER, "Bearer " + token);
                applier.apply(headers);
            } catch (Throwable t) {
                applier.fail(Status.UNAUTHENTICATED.withCause(t));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {}
}

