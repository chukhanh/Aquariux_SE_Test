package org.aquariux.cryptotrade.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aquariux.cryptotrade.common.RestTemplateApi;
import org.aquariux.cryptotrade.dto.PriceDto;
import org.aquariux.cryptotrade.entity.BestPriceEntity;
import org.aquariux.cryptotrade.enumData.Exchange;
import org.aquariux.cryptotrade.repository.BestPriceRepository;
import org.aquariux.cryptotrade.service.PriceAggregatorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PriceAggregatorServiceImpl implements PriceAggregatorService {

    private final RestTemplateApi restTemplateApi;
    private final BestPriceRepository bestPriceRepository;

    @Value(value = "${api.ticker.bookTicker}")
    private String bookTickerURL;

    @Value(value = "${api.ticker.market}")
    private String tickerURL;


    @Override
    public List<PriceDto> fetchPrice() {
        CompletableFuture<List<Map<String, Object>>> binanceFuture =
                restTemplateApi.callApiAsync(
                        bookTickerURL,
                        new ParameterizedTypeReference<List<Map<String, Object>>>() {},
                        HttpMethod.GET
                );

        CompletableFuture<List<Map<String, Object>>> huobiFuture =
                restTemplateApi.callApiAsync(
                        tickerURL,
                        new ParameterizedTypeReference<List<Map<String, Object>>>() {},
                        HttpMethod.GET
                );

        CompletableFuture.allOf(binanceFuture, huobiFuture).join();

        List<PriceDto> results = new ArrayList<>();

        List<Map<String, Object>> binanceRaw = binanceFuture.join();
        if (binanceRaw != null) {
            results.addAll(Exchange.BINANCE.convert(binanceRaw));
        }

        List<Map<String, Object>> huobiRaw = huobiFuture.join();
        if (huobiRaw != null) {
            results.addAll(Exchange.HUOBI.convert(huobiRaw));
        }

        return results;
    }

    @Override
    @Transactional
    public void saveAllBestPrice(String symbol, List<PriceDto> prices) {
        // Best bid = max bidPrice
        PriceDto bestBid = prices.stream()
                .max(Comparator.comparing(PriceDto::getBidPrice))
                .orElse(null);

        // Best ask = min askPrice
        PriceDto bestAsk = prices.stream()
                .min(Comparator.comparing(PriceDto::getAskPrice))
                .orElse(null);

        BestPriceEntity entity = bestPriceRepository.findBySymbol(symbol)
                .orElseGet(() -> {
                    BestPriceEntity bp = new BestPriceEntity();
                    bp.setSymbol(symbol);
                    return bp;
                });
        entity.setBestBid(bestBid.getBidPrice());
        entity.setSourceOfBestBid(bestBid.getSource());

        entity.setBestAsk(bestAsk.getAskPrice());
        entity.setSourceOfBestAsk(bestAsk.getSource());

        entity.setUpdatedAt(Instant.now());

        bestPriceRepository.saveAndFlush(entity);
    }
}
