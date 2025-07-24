package io.github.nightfuryar.springwebsecuritydemo.auth.repository;

import io.github.nightfuryar.springwebsecuritydemo.auth.entity.AppUserAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserAuthorityRepository extends JpaRepository<AppUserAuthorityEntity, UUID> {
    Optional<AppUserAuthorityEntity> findByAuthorityName(String authorityName);
}
