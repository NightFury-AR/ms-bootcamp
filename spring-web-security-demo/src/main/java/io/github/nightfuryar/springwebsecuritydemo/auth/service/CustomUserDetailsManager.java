package io.github.nightfuryar.springwebsecuritydemo.auth.service;

import io.github.nightfuryar.springwebsecuritydemo.auth.dto.req.UserAuthorityRequest;
import io.github.nightfuryar.springwebsecuritydemo.auth.dto.req.UserRoleRequest;
import io.github.nightfuryar.springwebsecuritydemo.auth.entity.AppUserAuthorityEntity;
import io.github.nightfuryar.springwebsecuritydemo.auth.entity.AppUserEntity;
import io.github.nightfuryar.springwebsecuritydemo.auth.entity.AppUserRoleEntity;
import io.github.nightfuryar.springwebsecuritydemo.auth.repository.AppUserAuthorityRepository;
import io.github.nightfuryar.springwebsecuritydemo.auth.repository.AppUserRepository;
import io.github.nightfuryar.springwebsecuritydemo.auth.repository.AppUserRoleRepository;
import io.github.nightfuryar.springwebsecuritydemo.util.UserDetailsMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsManager implements UserDetailsManager {

    @Autowired private final AppUserRepository appUserRepository;
    @Autowired private final AppUserRoleRepository appUserRoleRepository;
    @Autowired private final AppUserAuthorityRepository appUserAuthorityRepository;
    @Autowired private final PasswordEncoder passwordEncoder;
    @Autowired private final UserDetailsMapper userDetailsMapper;

    @Override
    public void createUser(UserDetails user) {
        if (this.userExists(user.getUsername())) {
            throw new RuntimeException("User already exists with this username - "+user.getUsername());
        }
        AppUserRoleEntity roleUser = appUserRoleRepository
                .findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error while fetching roles from DB "));

        AppUserAuthorityEntity readPrivileges = appUserAuthorityRepository.findByAuthorityName("READ_PRIVILEGES")
                .orElseThrow(() -> new RuntimeException("Error while fetching authority from DB"));

        AppUserEntity entity = userDetailsMapper.toEntity(user, roleUser , readPrivileges);
        appUserRepository.save(entity);
    }

    @Override
    public void updateUser(UserDetails user) {
        AppUserEntity appUserEntity = appUserRepository
                .findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException(" user - " + user.getUsername() + " is not found"));
        appUserEntity.setPassword(user.getPassword());
        appUserRepository.save(appUserEntity);
    }

    @Override
    public void deleteUser(String username) {
        appUserRepository.deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            String currentUser = authentication.getName();
            AppUserEntity currentUserDetail = appUserRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new RuntimeException("INTERNAL_ERROR - CODE x APP0002"));
            if (passwordEncoder.matches(oldPassword,currentUserDetail.getPassword())) {
                currentUserDetail.setPassword(passwordEncoder.encode(newPassword));
                appUserRepository.save(currentUserDetail);
            } else {
                throw new RuntimeException("your old password is wrong !!! please try again later");
            }
        }
    }

    @Override
    public boolean userExists(String username) {
        return this.appUserRepository.findByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .map(userDetailsMapper::toUserDetails)
                .orElseThrow(() -> new RuntimeException(" user with name - "+username+" is not found !!!"));
    }

    public void updateUserRole(UserRoleRequest userRoleRequest) {
        AppUserEntity appUserEntity = appUserRepository.findByUsername(userRoleRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("no user found in database for - " + userRoleRequest.getUsername()));
        AppUserRoleEntity appUserRoleEntity = appUserRoleRepository.findByRoleName(userRoleRequest.getRole().getRoleName())
                .orElseThrow(() -> new RuntimeException("no role found in database with name - " + userRoleRequest.getRole()));
        Set<AppUserRoleEntity> roles = appUserEntity.getRoles();
        boolean roleAlreadyExist = roles.stream()
                .anyMatch(existingRole -> existingRole.getRoleName().equalsIgnoreCase(userRoleRequest.getRole().getRoleName()));
        if (roleAlreadyExist) {
            throw new RuntimeException(" This role is already exist for this user "+userRoleRequest.getUsername());
        }
        roles.add(appUserRoleEntity);
        appUserRepository.save(appUserEntity);
    }

    public void updateUserAuthority(UserAuthorityRequest userAuthorityRequest) {
        AppUserEntity appUserEntity = appUserRepository.findByUsername(userAuthorityRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("no user found in database for - " + userAuthorityRequest.getUsername()));
        AppUserAuthorityEntity appUserAuthorityEntity = appUserAuthorityRepository.findByAuthorityName(userAuthorityRequest.getAuthority().getAuthorityName())
                .orElseThrow(() -> new RuntimeException("no role found in database with name - " + userAuthorityRequest.getAuthority().getAuthorityName()));
        Set<AppUserAuthorityEntity> authorities = appUserEntity.getAuthorities();
        boolean authorityAlreadyExist = authorities.stream()
                .anyMatch(existingAuthority -> existingAuthority.getAuthorityName().equalsIgnoreCase(userAuthorityRequest.getAuthority().getAuthorityName()));
        if (authorityAlreadyExist) {
            throw new RuntimeException(" This role is already exist for this user "+userAuthorityRequest.getUsername());
        }
        authorities.add(appUserAuthorityEntity);
        appUserRepository.save(appUserEntity);
    }
}
