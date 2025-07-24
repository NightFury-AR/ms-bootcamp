package io.github.nightfuryar.springwebsecuritydemo.auth.dto.req;

import lombok.Data;

@Data
public class UserRoleRequest {
    private String username;
    private APP_ROLE role;
}
