package org.aquariux.cryptotrade.service;

import org.aquariux.cryptotrade.dto.TradeRequest;
import org.aquariux.cryptotrade.entity.TradeTransactionEntity;

import java.util.List;

public interface TradeService {
    TradeTransactionEntity executeTrade(TradeRequest request);

    List<TradeTransactionEntity> getHistory(String username);
}
