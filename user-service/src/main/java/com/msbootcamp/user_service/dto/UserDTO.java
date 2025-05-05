package com.msbootcamp.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID userId;
    private String username;
    private String email;
    private String city;
    private String status;
    private String createdAt;
    private String updatedAt;
}
