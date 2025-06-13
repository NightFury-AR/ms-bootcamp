package io.github.nightfuryar.springboot_grpc_demo.repository;

import io.github.nightfuryar.springboot_grpc_demo.entity.VehicleLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VehicleLocationRepository extends JpaRepository<VehicleLocationEntity,String> {
    List<VehicleLocationEntity> findByVehicleVehicleNumber(String vehicleNumber);
}
