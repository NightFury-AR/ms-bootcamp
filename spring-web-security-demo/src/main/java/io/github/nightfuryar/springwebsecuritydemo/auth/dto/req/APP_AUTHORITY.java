package io.github.nightfuryar.springwebsecuritydemo.auth.dto.req;

public enum APP_AUTHORITY {
    READ_PRIVILEGES("READ_PRIVILEGES"),
    WRITE_PRIVILEGES("WRITE_PRIVILEGES"),
    DELETE_PRIVILEGES("DELETE_PRIVILEGES");

    private final String authorityName;

    APP_AUTHORITY(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthorityName() {
        return authorityName;
    }
}
