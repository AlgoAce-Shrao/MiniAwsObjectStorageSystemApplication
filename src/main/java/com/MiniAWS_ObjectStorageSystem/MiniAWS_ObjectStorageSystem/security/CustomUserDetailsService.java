package com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.security;

import com.MiniAWS_ObjectStorageSystem.MiniAWS_ObjectStorageSystem.Repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;


    @Override
    public @Nullable UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return appUserRepository.findByUsername(username).orElse(null);
    }
}
