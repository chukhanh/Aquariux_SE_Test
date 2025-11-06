package org.aquariux.cryptotrade.service;

import org.aquariux.cryptotrade.entity.BestPriceEntity;

import java.util.Optional;

public interface BestPriceService {
    Optional<BestPriceEntity> findBySymbol(String symbol);
}
