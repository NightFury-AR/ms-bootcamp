package io.github.nightfuryar.springwebsecuritydemo.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "authorities")
@Getter
@Setter
public class AppUserAuthorityEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String authorityName; // e.g., "READ_PRODUCT", "DELETE_USER"
}

