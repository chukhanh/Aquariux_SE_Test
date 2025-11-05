# Aquariux_SE_Test
Software Engineer (Java_FinTech) Technical Test


## ✅ System Summary

| Feature | Description |
|--------|-------------|
| **Supported Trading Pairs** | `ETHUSDT`, `BTCUSDT` |
| **Initial User Balance** | 50,000 USDT (in H2 database) |
| **Price Aggregation** | Fetch prices from Binance & Huobi every **10 seconds** |
| **Best Price Logic** | **Best Bid** → used for SELL<br>**Best Ask** → used for BUY |
| **Database** | H2 in-memory, auto-created via JPA |
| **Tech Stack** | Spring Boot 3, JPA, H2, WebClient, Scheduler |
| **APIs Provided** | Get latest price, trade (buy/sell), wallet balance, trade history |
| **Concurrency Control** | Pessimistic lock on wallets during trading |
| **Assumptions** | User already authenticated; username available via `Principal` |



