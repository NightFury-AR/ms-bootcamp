package io.github.nightfuryar.springwebsecuritydemo.auth.controller;

import io.github.nightfuryar.springwebsecuritydemo.auth.dto.req.UserAuthorityRequest;
import io.github.nightfuryar.springwebsecuritydemo.auth.dto.req.UserRoleRequest;
import io.github.nightfuryar.springwebsecuritydemo.auth.dto.res.UserUpdateResponse;
import io.github.nightfuryar.springwebsecuritydemo.auth.service.CustomUserDetailsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@Slf4j
public class AdminController {

    @Autowired private CustomUserDetailsManager userService;

    @PostMapping("/update-role")
    public UserUpdateResponse updateUserRole(@RequestBody UserRoleRequest userRoleRequest) {
        log.info("Updating user role: {}", userRoleRequest.getUsername());
        userService.updateUserRole(userRoleRequest);
        return UserUpdateResponse.builder()
                .message("user role has been updated successfully")
                .username(userRoleRequest.getUsername())
                .build();
    }

    @PostMapping("/update-authority")
    public UserUpdateResponse updateUserAuthority(@RequestBody UserAuthorityRequest userAuthorityRequest) {
        log.info("Updating user authority: {}", userAuthorityRequest.getUsername());
        userService.updateUserAuthority(userAuthorityRequest);
        return UserUpdateResponse.builder()
                .message("user authority has been updated successfully")
                .username(userAuthorityRequest.getUsername())
                .build();
    }
}
