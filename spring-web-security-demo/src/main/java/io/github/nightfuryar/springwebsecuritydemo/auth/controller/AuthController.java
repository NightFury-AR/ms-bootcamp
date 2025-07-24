package io.github.nightfuryar.springwebsecuritydemo.auth.controller;

import io.github.nightfuryar.springwebsecuritydemo.auth.dto.req.UserLoginRequest;
import io.github.nightfuryar.springwebsecuritydemo.auth.dto.req.UserRegistrationRequest;
import io.github.nightfuryar.springwebsecuritydemo.auth.dto.req.UserUpdateRequest;
import io.github.nightfuryar.springwebsecuritydemo.auth.dto.res.AuthResponse;
import io.github.nightfuryar.springwebsecuritydemo.auth.dto.res.UserUpdateResponse;
import io.github.nightfuryar.springwebsecuritydemo.auth.security.JwtUtils;
import io.github.nightfuryar.springwebsecuritydemo.auth.service.CustomUserDetailsManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    @Autowired private final CustomUserDetailsManager customUserDetailsManager;
    @Autowired private final AuthenticationManager authenticationManager;
    @Autowired private final JwtUtils jwtUtils;
    @Autowired private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public AuthResponse registerUser(@RequestBody UserRegistrationRequest user) {
        log.info("Registering user: {}", user.getUsername());
        // TODO : custom child UserDetails object for custom fields
        UserDetails newUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        customUserDetailsManager.createUser(newUser);
        return AuthResponse.builder()
                .username(user.getUsername())
                .message("User registered successfully , try logging in now!")
                .build();
    }

    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        log.info("Logging in user: {}", userLoginRequest.getUsername());
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequest.getUsername(),
                            userLoginRequest.getPassword()
                    )
            );
            log.info("User authenticated successfully: {}", authenticate.getName());
            String jwtToken = jwtUtils.generateToken((UserDetails) authenticate.getPrincipal());
            log.info("Generated JWT Token: {}", jwtToken);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .username(userLoginRequest.getUsername())
                    .message("User logged in successfully!")
                    .build();
        } catch (Exception ex) {
            log.error("Authentication failed: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


    @PostMapping("/update")
    public UserUpdateResponse updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        log.info("Updating user: {}", userUpdateRequest.getUsername());
        // TODO : custom child UserDetails object for custom fields
        UserDetails updatedUser = User.builder().username(userUpdateRequest.getUsername()).build();
        customUserDetailsManager.updateUser(updatedUser);
        return UserUpdateResponse.builder()
                .username(userUpdateRequest.getUsername())
                .message("User updated successfully!")
                .build();
    }

}

