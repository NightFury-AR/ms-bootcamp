package io.github.nightfuryar.springwebsecuritydemo.auth.dto.req;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String email;
}
