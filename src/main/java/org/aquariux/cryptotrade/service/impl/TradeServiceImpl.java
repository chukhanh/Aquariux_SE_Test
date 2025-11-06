package org.aquariux.cryptotrade.service.impl;

import lombok.RequiredArgsConstructor;
import org.aquariux.cryptotrade.dto.TradeRequest;
import org.aquariux.cryptotrade.entity.BestPriceEntity;
import org.aquariux.cryptotrade.entity.TradeTransactionEntity;
import org.aquariux.cryptotrade.entity.UserEntity;
import org.aquariux.cryptotrade.entity.WalletBalanceEntity;
import org.aquariux.cryptotrade.repository.BestPriceRepository;
import org.aquariux.cryptotrade.repository.TradeTransactionRepository;
import org.aquariux.cryptotrade.repository.UserRepository;
import org.aquariux.cryptotrade.repository.WalletBalanceRepository;
import org.aquariux.cryptotrade.service.TradeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final BestPriceRepository bestPriceRepository;
    private final WalletBalanceRepository walletBalanceRepository;
    private final TradeTransactionRepository tradeTransactionRepository;
    private final UserRepository userRepository;
    @Override
    public TradeTransactionEntity executeTrade(TradeRequest req) {
        String symbol = req.getSymbol().toUpperCase();
        String side = req.getSide().toUpperCase();
        String username = req.getUsername();

        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
        BigDecimal qty = req.getQuantity();

        BestPriceEntity best = bestPriceRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Best price not found for: " + symbol));

        BigDecimal price = side.equals("BUY") ? best.getBestAsk() : best.getBestBid();
        if (price == null) throw new RuntimeException("Price unavailable for " + symbol);

        BigDecimal total = price.multiply(qty);

        String baseAsset = symbol.replace("USDT", "");
        String quoteAsset = "USDT";



        WalletBalanceEntity quoteWallet = walletBalanceRepository
                .findByUserIdAndAsset(user.getID(), quoteAsset)
                .orElseThrow(() -> new RuntimeException("User does not have USDT wallet"));

        WalletBalanceEntity baseWallet = walletBalanceRepository
                .findByUserIdAndAsset(user.getID(), baseAsset)
                .orElseGet(() -> {
                    WalletBalanceEntity w = new WalletBalanceEntity();
                    w.setUserId(user.getID());
                    w.setAsset(baseAsset);
                    w.setAmount(BigDecimal.ZERO);
                    return walletBalanceRepository.save(w);
                });

        if (side.equals("BUY")) {
            if (quoteWallet.getAmount().compareTo(total) < 0)
                throw new RuntimeException("Insufficient USDT balance.");

            quoteWallet.setAmount(quoteWallet.getAmount().subtract(total));
            baseWallet.setAmount(baseWallet.getAmount().add(qty));
        } else if (side.equals("SELL")) {
            if (baseWallet.getAmount().compareTo(qty) < 0)
                throw new RuntimeException("Insufficient " + baseAsset + " balance.");

            baseWallet.setAmount(baseWallet.getAmount().subtract(qty));
            quoteWallet.setAmount(quoteWallet.getAmount().add(total));
        }else {
            throw new RuntimeException("Invalid trade side (BUY or SELL)");
        }
        walletBalanceRepository.save(quoteWallet);
        walletBalanceRepository.save(baseWallet);

        TradeTransactionEntity tx = new TradeTransactionEntity();
        tx.setUserId(user.getID());
        tx.setSymbol(symbol);
        tx.setSide(side);
        tx.setPrice(price);
        tx.setQuantity(qty);
        tx.setTotal(total);
        tx.setCreatedAt(Instant.now());

        return tradeTransactionRepository.save(tx);
    }

    @Override
    public List<TradeTransactionEntity> getHistory(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
        return tradeTransactionRepository.findByUserIdOrderByCreatedAtDesc(user.getID());
    }
}
