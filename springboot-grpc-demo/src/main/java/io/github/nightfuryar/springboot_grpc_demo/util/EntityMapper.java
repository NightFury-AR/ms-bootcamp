package io.github.nightfuryar.springboot_grpc_demo.util;

import io.github.nightfuryar.springboot_grpc_demo.entity.VehicleEntity;
import io.github.nightfuryar.springboot_grpc_demo.entity.VehicleLocationEntity;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleLocation;
import io.github.nightfuryar.springbootgrpcdemo.locationtracker.v1.VehicleRegistrationRequest;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Component
public class EntityMapper {

    public VehicleEntity toVehicleEntity(VehicleRegistrationRequest vehicleRegistrationRequest) {
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setVehicleNumber(vehicleRegistrationRequest.getVehicleNumber());
        vehicleEntity.setVehicleType(vehicleRegistrationRequest.getVehicleType());
        vehicleEntity.setOwnerName(vehicleRegistrationRequest.getOwnerName());
        vehicleEntity.setLocationHistory(Collections.emptyList());
        return vehicleEntity;
    }

    public VehicleLocationEntity toLocationEntity(VehicleEntity vehicle,VehicleLocation vehicleLocation) {
        VehicleLocationEntity entity = new VehicleLocationEntity();
        entity.setLatitude(vehicleLocation.getLatitude());
        entity.setLongitude(vehicleLocation.getLongitude());
        entity.setTimestamp(formatTimestamp(vehicleLocation.getTimestamp()));
        entity.setVehicle(vehicle);
        return entity;
    }

    private String formatTimestamp(long timestampMillis) {
        Instant instant = Instant.ofEpochMilli(timestampMillis);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

}
