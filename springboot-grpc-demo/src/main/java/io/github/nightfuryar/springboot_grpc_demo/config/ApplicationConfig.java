package io.github.nightfuryar.springboot_grpc_demo.config;

import io.github.nightfuryar.springboot_grpc_demo.interceptor.client.AuthClientInterceptor;
import io.github.nightfuryar.springboot_grpc_demo.interceptor.client.ClientLoggingInterceptor;
import io.github.nightfuryar.springboot_grpc_demo.interceptor.server.LTServerInterceptor;
import io.grpc.ClientInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.grpc.Channel;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationTrackerServiceGrpc;
import org.springframework.grpc.autoconfigure.server.GrpcServerFactoryAutoConfiguration;
import org.springframework.grpc.autoconfigure.server.GrpcServerProperties;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.grpc.server.GrpcServerFactory;

@Configuration
public class ApplicationConfig {

    @Autowired
    private GrpcChannelFactory grpcChannelFactory;
    @Autowired private AuthClientInterceptor authClientInterceptor;
    @Autowired private ClientLoggingInterceptor clientLoggingInterceptor;

    @Bean
    public LocationTrackerServiceGrpc.LocationTrackerServiceBlockingStub locationTrackerBlockingStub() {
        Channel channel = grpcChannelFactory.createChannel("location-tracker"); // name from config
        return LocationTrackerServiceGrpc
                .newBlockingStub(channel)
                .withInterceptors(authClientInterceptor,clientLoggingInterceptor);
    }

    @Bean
    public LocationTrackerServiceGrpc.LocationTrackerServiceStub locationTrackerAsyncStub() {
        Channel channel = grpcChannelFactory.createChannel("location-tracker");
        return LocationTrackerServiceGrpc
                .newStub(channel)
                .withInterceptors(authClientInterceptor,clientLoggingInterceptor);
    }

}

