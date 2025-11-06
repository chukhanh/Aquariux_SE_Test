package org.aquariux.cryptotrade.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquariux.cryptotrade.entity.UserEntity;
import org.aquariux.cryptotrade.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(encoder.encode(user.getPassword()))
                .roles(user.getRole())
                .build();
    }
}
