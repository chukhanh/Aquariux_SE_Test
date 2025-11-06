package org.aquariux.cryptotrade.repository;

import org.aquariux.cryptotrade.entity.WalletBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletBalanceRepository extends JpaRepository<WalletBalanceEntity, Long> {
    Optional<WalletBalanceEntity> findByUserIdAndAsset(Long userId, String quoteAsset);
    List<WalletBalanceEntity> findByUserId(Long userId);
}
