package org.aquariux.cryptotrade.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PriceDto {
    private String symbol;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
    private BigDecimal bidQty;
    private BigDecimal askQty;
    private String source;
}
