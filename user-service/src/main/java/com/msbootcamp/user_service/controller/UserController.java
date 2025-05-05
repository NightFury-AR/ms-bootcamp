package com.msbootcamp.user_service.controller;

import com.msbootcamp.user_service.dto.UpdateUser;
import com.msbootcamp.user_service.dto.UserDTO;
import com.msbootcamp.user_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user-service")
@AllArgsConstructor
public class UserController {

    @Autowired private final UserService userService;

    @PostMapping
    public UserDTO createUser(UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @DeleteMapping
    public void deleteAllUsers() {
        userService.deleteAll();
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable String userId) {
        return userService.findById(userId);
    }

    @PutMapping("/{userId}")
    public UserDTO updateUser(@PathVariable String userId, @RequestBody UpdateUser userDTO) {
        return userService.update(userDTO, UUID.fromString(userId));
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable String userId) {
        userService.deleteById(UUID.fromString(userId));
    }

}
