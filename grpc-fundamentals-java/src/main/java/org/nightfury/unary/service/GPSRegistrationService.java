package org.nightfury.unary.service;


import io.grpc.stub.StreamObserver;
import org.nightfury.proto.unary.gpsregistration.v1.VehicleRegistrationRequest;
import org.nightfury.proto.unary.gpsregistration.v1.VehicleRegistrationResponse;
import org.nightfury.proto.unary.gpsregistration.v1.GPSRegistrationServiceGrpc;

public class GPSRegistrationService extends GPSRegistrationServiceGrpc.GPSRegistrationServiceImplBase {


    @Override
    public void registerVehicle(VehicleRegistrationRequest request, StreamObserver<VehicleRegistrationResponse> responseObserver) {
        responseObserver.onNext(
                VehicleRegistrationResponse.newBuilder()
                        .setMessage(" Vehicle - "+request.getVehicleName()+" with id - "+request.getVehicleId()+" registered successfully !!!")
                        .build()
        );

        responseObserver.onCompleted();
    }
}
