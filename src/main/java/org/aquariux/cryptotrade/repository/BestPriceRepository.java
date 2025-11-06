package org.aquariux.cryptotrade.repository;

import org.aquariux.cryptotrade.entity.BestPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BestPriceRepository extends JpaRepository<BestPriceEntity, Long> {
    Optional<BestPriceEntity> findBySymbol(String symbol);
}
