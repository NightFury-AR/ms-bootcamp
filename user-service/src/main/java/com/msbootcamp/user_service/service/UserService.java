package com.msbootcamp.user_service.service;

import com.msbootcamp.user_service.dto.UpdateUser;
import com.msbootcamp.user_service.dto.UserDTO;
import com.msbootcamp.user_service.entity.User;
import com.msbootcamp.user_service.mapper.UserMapper;
import com.msbootcamp.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired private final UserRepository userRepository;
    @Autowired private final UserMapper userMapper;

    public UserDTO findById(String username) {
        Optional<User> existingUser = userRepository.findById(UUID.fromString(username));
        return existingUser
                .map(userMapper::toDTO)
                .get();
    }


    public UserDTO save(UserDTO userDTO) {
        User newUser = userRepository.save(userMapper.toEntity(userDTO));
        return userMapper.toDTO(newUser);
    }

    public void deleteById(UUID userId) {
        userRepository.deleteById(userId);
    }

    public List<UserDTO> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }


    public UserDTO update(UpdateUser userDTO,UUID userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setEmail(userDTO.getEmail());
                    user.setCity(userDTO.getCity());
                    user.setStatus(userDTO.getStatus());
                    return userRepository.save(user);
                })
                .map(userMapper::toDTO)
                .get();
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }



}
