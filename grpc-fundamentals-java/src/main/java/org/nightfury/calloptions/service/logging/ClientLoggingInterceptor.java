package org.nightfury.calloptions.service.logging;

import io.grpc.*;

public class ClientLoggingInterceptor implements ClientInterceptor {

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions,
            Channel next) {
        System.out.println("Call options: " + callOptions);
        System.out.println("Channel Level Authority: " + next.authority());
        ClientCall<ReqT, RespT> reqTRespTClientCall = next.newCall(method, callOptions);
        return new LoggingClientCall<>(reqTRespTClientCall, method);
    }

}
