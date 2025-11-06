package org.aquariux.cryptotrade.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TradeRequest {
    private String symbol;
    private String side;
    private BigDecimal quantity;
    private String username;
}