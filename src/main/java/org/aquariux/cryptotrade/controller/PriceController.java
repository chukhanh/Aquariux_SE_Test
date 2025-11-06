package org.aquariux.cryptotrade.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aquariux.cryptotrade.service.BestPriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
@Log4j2
public class PriceController {

    private final BestPriceService bestPriceService;

    @GetMapping("/{symbol}")
    public ResponseEntity<?> getLatestPrice(@PathVariable String symbol) {
        return bestPriceService.findBySymbol(symbol.toUpperCase())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
