package com.sda.clinicapi.service;

import com.sda.clinicapi.model.User;
import com.sda.clinicapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return new BridgeUser(user);
    }

    private static class BridgeUser extends User implements UserDetails {

        public BridgeUser(User user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(getRole().name()));
        }

        @Override
        public boolean isAccountNonExpired() {
            return isEnabled();
        }

        @Override
        public boolean isAccountNonLocked() {
            return isEnabled();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return isEnabled();
        }
    }
}


