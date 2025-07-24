package io.github.nightfuryar.springwebsecuritydemo.auth.dto.req;

import lombok.Data;

@Data
public class UserAuthorityRequest {
    private String username;
    private APP_AUTHORITY authority;
}
