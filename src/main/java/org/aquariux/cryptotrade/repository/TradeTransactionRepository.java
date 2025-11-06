package org.aquariux.cryptotrade.repository;

import org.aquariux.cryptotrade.entity.TradeTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeTransactionRepository extends JpaRepository<TradeTransactionEntity, Long> {
    List<TradeTransactionEntity> findByUserIdOrderByCreatedAtDesc(Long id);
}
