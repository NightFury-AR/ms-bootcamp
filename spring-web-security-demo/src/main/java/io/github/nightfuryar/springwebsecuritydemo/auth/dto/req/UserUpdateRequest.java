package io.github.nightfuryar.springwebsecuritydemo.auth.dto.req;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String password;
    private String username;
    private String email;
}
