package io.github.nightfuryar.springwebsecuritydemo.auth.service;

import io.github.nightfuryar.springwebsecuritydemo.auth.repository.AppUserRepository;
import io.github.nightfuryar.springwebsecuritydemo.util.UserDetailsMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


// no usage , as we have UserDetailsManager impl already

@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    //@Autowired
    private final AppUserRepository appUserRepository;
    //@Autowired
    private final UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository
                .findByUsername(username)
                .map(userDetailsMapper::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

}
