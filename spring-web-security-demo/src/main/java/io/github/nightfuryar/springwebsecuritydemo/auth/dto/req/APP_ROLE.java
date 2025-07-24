package io.github.nightfuryar.springwebsecuritydemo.auth.dto.req;

public enum APP_ROLE {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String roleName;

    APP_ROLE(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
