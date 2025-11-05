package org.aquariux.cryptotrade.service;

import org.aquariux.cryptotrade.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserEntity findUserByUsername(String username);
}
