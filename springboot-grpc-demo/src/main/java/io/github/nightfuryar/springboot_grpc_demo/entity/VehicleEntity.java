package io.github.nightfuryar.springboot_grpc_demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "vehicle_details")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or AUTO if preferred
    @Column(name = "vehicle_id")
    private long vehicleId;
    private String vehicleNumber;
    private String vehicleType;
    private String ownerName;
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleLocationEntity> locationHistory = new ArrayList<>();
}
