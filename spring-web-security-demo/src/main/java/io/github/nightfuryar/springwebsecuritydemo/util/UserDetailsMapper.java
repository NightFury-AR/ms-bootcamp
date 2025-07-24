package io.github.nightfuryar.springwebsecuritydemo.util;

import io.github.nightfuryar.springwebsecuritydemo.auth.entity.AppUserAuthorityEntity;
import io.github.nightfuryar.springwebsecuritydemo.auth.entity.AppUserEntity;
import io.github.nightfuryar.springwebsecuritydemo.auth.entity.AppUserRoleEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class UserDetailsMapper {

    public AppUserEntity toEntity(UserDetails userDetails, AppUserRoleEntity role , AppUserAuthorityEntity privilege) {
        AppUserEntity entity = new AppUserEntity();
        entity.setUsername(userDetails.getUsername());
        entity.setPassword(userDetails.getPassword());
        entity.setEnabled(userDetails.isEnabled());
        entity.setLocked(!userDetails.isAccountNonLocked());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setAuthorities(Set.of(privilege));
        entity.setRoles(Set.of(role));
        return entity;
    }

    public UserDetails toUserDetails(AppUserEntity appUser) {
        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .accountExpired(!appUser.isEnabled())
                .accountLocked(appUser.isLocked())
                .credentialsExpired(!appUser.isEnabled())
                .disabled(appUser.isLocked())
                .roles(String.valueOf(appUser.getRoles().stream().map(AppUserRoleEntity::getRoleName).toList()))
                .authorities(getRolesAndPrivileges(appUser))
                .build();
    }

    private Collection<? extends GrantedAuthority> getRolesAndPrivileges(AppUserEntity user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().stream()
                .map(AppUserRoleEntity::getRoleName)
                .map(SimpleGrantedAuthority::new)
                .forEach(authorities::add);
        user.getAuthorities().stream()
                .map(AppUserAuthorityEntity::getAuthorityName)
                .map(SimpleGrantedAuthority::new)
                .forEach(authorities::add);
        return authorities;
    }
}
