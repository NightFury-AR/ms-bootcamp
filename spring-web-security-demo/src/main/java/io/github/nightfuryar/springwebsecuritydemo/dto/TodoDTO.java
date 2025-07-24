package io.github.nightfuryar.springwebsecuritydemo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class TodoDTO {
    private UUID id;
    private String title;
    private boolean isCompleted;
}


