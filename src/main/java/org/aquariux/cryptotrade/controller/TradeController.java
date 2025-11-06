package org.aquariux.cryptotrade.controller;

import lombok.RequiredArgsConstructor;
import org.aquariux.cryptotrade.dto.TradeRequest;
import org.aquariux.cryptotrade.service.TradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
    public ResponseEntity<?> trade(
            @RequestBody TradeRequest request) {

        return ResponseEntity.ok(tradeService.executeTrade(request));
    }

    @GetMapping
    public ResponseEntity<?> getHistory(@RequestParam(name = "username") String username) {
        return ResponseEntity.ok(tradeService.getHistory(username));
    }
}
