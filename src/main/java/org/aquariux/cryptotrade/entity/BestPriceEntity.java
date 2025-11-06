package org.aquariux.cryptotrade.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "best_prices")
@Data
@Getter
@Setter
public class BestPriceEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String symbol;
    private BigDecimal bestBid;
    private BigDecimal bestAsk;
    private String sourceOfBestBid;
    private String sourceOfBestAsk;
    private Instant updatedAt;
}
