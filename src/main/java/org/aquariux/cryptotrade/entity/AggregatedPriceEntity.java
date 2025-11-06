package org.aquariux.cryptotrade.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "aggregated_prices")
@Data
@Getter
@Setter
public class AggregatedPriceEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String symbol;
    private String source;
    private BigDecimal bid;
    private BigDecimal ask;
    private Instant timestamp;
}
