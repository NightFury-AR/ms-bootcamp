package io.github.nightfuryar.springwebsecuritydemo.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class AppUserRoleEntity {
    @Id
    @GeneratedValue
    private UUID roleId;

    @Column(nullable = false, unique = true)
    private String roleName; // e.g., "ROLE_USER", "ROLE_ADMIN"
}

