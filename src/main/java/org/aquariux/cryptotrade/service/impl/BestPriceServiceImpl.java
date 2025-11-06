package org.aquariux.cryptotrade.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquariux.cryptotrade.entity.BestPriceEntity;
import org.aquariux.cryptotrade.repository.BestPriceRepository;
import org.aquariux.cryptotrade.service.BestPriceService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BestPriceServiceImpl implements BestPriceService {

    private final BestPriceRepository bestPriceRepository;
    @Override
    public Optional<BestPriceEntity> findBySymbol(String symbol) {
        return bestPriceRepository.findBySymbol(symbol);
    }
}
