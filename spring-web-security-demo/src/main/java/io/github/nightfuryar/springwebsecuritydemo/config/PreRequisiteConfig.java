package io.github.nightfuryar.springwebsecuritydemo.config;

import io.github.nightfuryar.springwebsecuritydemo.auth.entity.AppUserAuthorityEntity;
import io.github.nightfuryar.springwebsecuritydemo.auth.entity.AppUserEntity;
import io.github.nightfuryar.springwebsecuritydemo.auth.entity.AppUserRoleEntity;
import io.github.nightfuryar.springwebsecuritydemo.auth.repository.AppUserAuthorityRepository;
import io.github.nightfuryar.springwebsecuritydemo.auth.repository.AppUserRepository;
import io.github.nightfuryar.springwebsecuritydemo.auth.repository.AppUserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;


@Component @Slf4j
public class PreRequisiteConfig implements CommandLineRunner {

    @Autowired private AppUserRepository appUserRepository;
    @Autowired private AppUserRoleRepository appUserRoleRepository;
    @Autowired private AppUserAuthorityRepository appUserAuthorityRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info(" setting up pre-requisites ... ");
        // prepare pre-requisite data
        //1. roles and privileges
        AppUserAuthorityEntity readPrivileges = new AppUserAuthorityEntity();
        readPrivileges.setAuthorityName("READ_PRIVILEGES");
        AppUserAuthorityEntity writePrivileges = new AppUserAuthorityEntity();
        writePrivileges.setAuthorityName("WRITE_PRIVILEGES");

        AppUserAuthorityEntity read = appUserAuthorityRepository.save(readPrivileges);
        AppUserAuthorityEntity write = appUserAuthorityRepository.save(writePrivileges);

        AppUserRoleEntity userRole = new AppUserRoleEntity();
        userRole.setRoleName("ROLE_USER");
        AppUserRoleEntity adminRole = new AppUserRoleEntity();
        adminRole.setRoleName("ROLE_ADMIN");

        AppUserRoleEntity admin = appUserRoleRepository.save(userRole);
        AppUserRoleEntity user = appUserRoleRepository.save(adminRole);


        //2.admin-user
        AppUserEntity adminUser = new AppUserEntity();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("1234"));
        adminUser.setLocked(false);
        adminUser.setEnabled(true);
        adminUser.setCreatedAt(LocalDateTime.now());
        adminUser.setUpdatedAt(LocalDateTime.now());
        adminUser.setRoles(Set.of(admin,user));
        adminUser.setAuthorities(Set.of(read, write));
        appUserRepository.save(adminUser);
        log.info(" pre-requisites completed. required configs has been loaded !");
    }
}
