package org.aquariux.cryptotrade.service;

import org.aquariux.cryptotrade.entity.WalletBalanceEntity;

import java.util.List;

public interface WalletService {
    List<WalletBalanceEntity> getWalletByUser(String username);
}
