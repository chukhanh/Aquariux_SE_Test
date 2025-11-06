package org.aquariux.cryptotrade.scheduler;

import lombok.RequiredArgsConstructor;
import org.aquariux.cryptotrade.dto.PriceDto;
import org.aquariux.cryptotrade.service.PriceAggregatorService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceAggregatorScheduler {
    private final PriceAggregatorService priceAggregatorService;

    @Scheduled(fixedRate = 10_000)
    public void fetchAndAggregate() {
        List<PriceDto> prices = priceAggregatorService.fetchPrice();

        Map<String, List<PriceDto>> grouped = prices.stream()
                .collect(Collectors.groupingBy(PriceDto::getSymbol));

        grouped.forEach(priceAggregatorService::saveAllBestPrice);
    }

}
