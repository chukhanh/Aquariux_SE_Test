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
@Table(name = "trade_transactions")
@Data
@Getter
@Setter
public class TradeTransactionEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String symbol;
    private String side; // BUY or SELL
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal total;
    private Instant createdAt;
}
