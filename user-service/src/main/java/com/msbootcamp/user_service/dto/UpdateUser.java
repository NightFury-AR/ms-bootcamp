package com.msbootcamp.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {
    private String email;
    private String city;
    private String status;
}
