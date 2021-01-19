package com.electroeing.authserver.service;

import com.electroeing.authserver.model.CustomUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final OAuthUserServiceImpl oAuthUserService;

    public CustomUserDetailsService(OAuthUserServiceImpl oAuthUserService) {
        this.oAuthUserService = oAuthUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return oAuthUserService.getUserDetails(username)
                .map(CustomUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found!"));
    }
}
