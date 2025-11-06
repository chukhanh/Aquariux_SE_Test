package org.aquariux.cryptotrade.service;

import org.aquariux.cryptotrade.dto.PriceDto;

import java.util.List;

public interface PriceAggregatorService {
    List<PriceDto> fetchPrice();

    void saveAllBestPrice(String symbol,  List<PriceDto> prices);
}
