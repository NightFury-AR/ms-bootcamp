package io.github.nightfuryar.springboot_grpc_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.grpc.Channel;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.LocationTrackerServiceGrpc;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class GRPCClientConfig {

    @Autowired
    private GrpcChannelFactory grpcChannelFactory;

    @Bean
    public LocationTrackerServiceGrpc.LocationTrackerServiceBlockingStub locationTrackerBlockingStub() {
        Channel channel = grpcChannelFactory.createChannel("location-tracker"); // name from config
        return LocationTrackerServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public LocationTrackerServiceGrpc.LocationTrackerServiceStub locationTrackerAsyncStub() {
        Channel channel = grpcChannelFactory.createChannel("location-tracker");
        return LocationTrackerServiceGrpc.newStub(channel);
    }

}

