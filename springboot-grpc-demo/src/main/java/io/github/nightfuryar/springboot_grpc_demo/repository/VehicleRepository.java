package io.github.nightfuryar.springboot_grpc_demo.repository;

import io.github.nightfuryar.springboot_grpc_demo.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    Optional<VehicleEntity> findByVehicleNumber(String vehicleNumber);
}
