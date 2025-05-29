package org.nightfury.unary.demo;




import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.nightfury.proto.unary.gpsregistration.v1.GPSRegistrationServiceGrpc;
import org.nightfury.proto.unary.gpsregistration.v1.VehicleRegistrationRequest;
import org.nightfury.proto.unary.gpsregistration.v1.VehicleRegistrationResponse;

public class GRPCUnaryClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext() // No SSL
                .build();

        GPSRegistrationServiceGrpc.GPSRegistrationServiceBlockingStub gpsRegistrationServiceBlockingStub = GPSRegistrationServiceGrpc.newBlockingStub(channel);


        VehicleRegistrationRequest request = VehicleRegistrationRequest.newBuilder()
                .setVehicleId("007")
                .setVehicleName("Bond")
                .build();

        VehicleRegistrationResponse response = gpsRegistrationServiceBlockingStub.registerVehicle(request);
        System.out.println("Server replied: " + response.getMessage());

        channel.shutdown();
    }
}

