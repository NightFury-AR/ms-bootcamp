package io.github.nightfuryar.springwebsecuritydemo.auth.repository;

import io.github.nightfuryar.springwebsecuritydemo.auth.entity.AppUserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRoleRepository  extends JpaRepository<AppUserRoleEntity, UUID> {
    Optional<AppUserRoleEntity> findByRoleName(String role);
}
