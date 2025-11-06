package org.aquariux.cryptotrade.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquariux.cryptotrade.entity.UserEntity;
import org.aquariux.cryptotrade.entity.WalletBalanceEntity;
import org.aquariux.cryptotrade.repository.UserRepository;
import org.aquariux.cryptotrade.repository.WalletBalanceRepository;
import org.aquariux.cryptotrade.service.WalletService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletBalanceRepository walletBalanceRepository;
    private final UserRepository userRepository;
    @Override
    public List<WalletBalanceEntity> getWalletByUser(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
        return walletBalanceRepository.findByUserId(user.getID());
    }
}
