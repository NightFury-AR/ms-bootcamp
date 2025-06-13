package io.github.nightfuryar.springboot_grpc_demo.util;


import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleRegistrationRequest;
import io.grpc.Status;
import io.grpc.StatusException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationTrackerValidator {

    public Optional<Status> validateVehicleRegistrationRequest(VehicleRegistrationRequest request) {
        if (request != null && request.getVehicleNumber() == null) {
            return Optional.of(Status.INVALID_ARGUMENT.withDescription("VehicleId cannot be empty !"));
        } else if (request != null && (request.getOwnerName() == null || request.getOwnerName().isEmpty())) {
            return Optional.of(Status.INVALID_ARGUMENT.withDescription("owner name cannot be empty !"));
        } else {
            return Optional.empty();
        }
    }

}
