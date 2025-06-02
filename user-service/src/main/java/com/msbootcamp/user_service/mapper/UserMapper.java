package com.msbootcamp.user_service.mapper;


import com.msbootcamp.user_service.dto.UserDTO;
import com.msbootcamp.user_service.entity.User;

public class UserMapper {

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getCity(),
                user.getStatus(),
                user.getCreatedAt().toString(),
                user.getUpdatedAt().toString()
        );
    }

    public User toEntity(UserDTO userDTO) {
        return new User(
                userDTO.getUserId(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getCity(),
                userDTO.getStatus()
        );
    }


}
