package org.aquariux.cryptotrade.repository;

import org.aquariux.cryptotrade.entity.AggregatedPriceEntity;
import org.aquariux.cryptotrade.entity.BestPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AggregatedPriceRepository extends JpaRepository<AggregatedPriceEntity, Long> {
}
