package org.aquariux.cryptotrade.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet_balances",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","asset"}))
@Data
@Getter
@Setter
public class WalletBalanceEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String asset;
    private BigDecimal amount;
}
