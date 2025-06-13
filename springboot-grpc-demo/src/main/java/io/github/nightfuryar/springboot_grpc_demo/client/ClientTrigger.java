package io.github.nightfuryar.springboot_grpc_demo.client;

import io.github.nightfuryar.springboot_grpc_demo.client.bidirectional.TrackLiveLocationsClient;
import io.github.nightfuryar.springboot_grpc_demo.client.clientstreaming.UploadLocationsClient;
import io.github.nightfuryar.springboot_grpc_demo.client.serverstreaming.LocationHistoryClient;
import io.github.nightfuryar.springboot_grpc_demo.client.unary.VehicleRegistrationClient;
import io.github.nightfuryar.springboot_grpc_demo.entity.VehicleEntity;
import io.github.nightfuryar.springboot_grpc_demo.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
@AllArgsConstructor
public class ClientTrigger implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final VehicleRegistrationClient vehicleRegistrationClient;
    private final UploadLocationsClient uploadLocationsClient;
    private final LocationHistoryClient locationHistoryClient;
    private final TrackLiveLocationsClient trackLiveLocationsClient;

    @Override
    public void run(String... args) throws Exception {

        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setVehicleNumber("TN78AA0000");
        vehicleEntity.setOwnerName("John Doe");
        vehicleEntity.setVehicleType("BIKE");
        vehicleEntity.setLocationHistory(new ArrayList<>());
        vehicleRepository.save(vehicleEntity);

        System.out.println("saved vehicle: " + vehicleEntity);

        // 1. unary [ client -> server -> client]
        vehicleRegistrationClient.retrieve();

        // 2. client streaming [ client - - - - > server -> client ]
        uploadLocationsClient.retrieve();

        Thread.sleep(5000); // Wait for the server to process the uploaded locations
        //3. server streaming [ client -> server - - - - > client ]
        locationHistoryClient.retrieve();

        //4. bi-directional [ client - - - > server - - - > client ]
        //trackLiveLocationsClient.retrieve();
    }
}
