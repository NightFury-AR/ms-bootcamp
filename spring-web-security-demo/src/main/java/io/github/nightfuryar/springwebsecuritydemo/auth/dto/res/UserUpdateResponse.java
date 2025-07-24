package io.github.nightfuryar.springwebsecuritydemo.auth.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class UserUpdateResponse {
    private String username;
    private String message;
}
