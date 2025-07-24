package io.github.nightfuryar.springwebsecuritydemo.auth.repository;

import io.github.nightfuryar.springwebsecuritydemo.auth.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, UUID> {
    Optional<AppUserEntity> findByUsername(String username);
    void deleteByUsername(String username);
}

