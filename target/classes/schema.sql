CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);


CREATE TABLE wallet_balances (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 user_id BIGINT NOT NULL,
                                 asset VARCHAR(16) NOT NULL,
                                 amount DECIMAL(30,8) NOT NULL,
                                 CONSTRAINT fk_wallet_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE aggregated_prices (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   symbol VARCHAR(16) NOT NULL, -- e.g. ETHUSDT, BTCUSDT
                                   source VARCHAR(32),          -- e.g. BINANCE, HUOBI
                                   bid DECIMAL(30,8),           -- bid price (price someone will SELL at) -> use for SELL orders
                                   ask DECIMAL(30,8),           -- ask price (price someone will BUY at) -> use for BUY orders
                                   timestamp TIMESTAMP NOT NULL
);

CREATE TABLE best_prices (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             symbol VARCHAR(16) NOT NULL UNIQUE,
                             best_bid DECIMAL(30,8),
                             best_ask DECIMAL(30,8),
                             source_of_best_bid VARCHAR(32),
                             source_of_best_ask VARCHAR(32),
                             updated_at TIMESTAMP NOT NULL
);

CREATE TABLE trade_transactions (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    user_id BIGINT NOT NULL,
                                    symbol VARCHAR(16) NOT NULL,
                                    side VARCHAR(4) NOT NULL, -- BUY or SELL
                                    price DECIMAL(30,8) NOT NULL,
                                    quantity DECIMAL(30,8) NOT NULL,
                                    total DECIMAL(30,8) NOT NULL, -- price * quantity
                                    created_at TIMESTAMP NOT NULL,
                                    CONSTRAINT fk_trade_user FOREIGN KEY (user_id) REFERENCES users(id)
);

--- Create one user
INSERT INTO users (username, password, role)
VALUES ('admin', 'admin', 'admin');

-- initial wallet for user 1: 50,000 USDT and 0 BTC/ETH
INSERT INTO wallet_balances (user_id, asset, amount) VALUES (1, 'USDT', 50000.00000000);
INSERT INTO wallet_balances (user_id, asset, amount) VALUES (1, 'BTC', 0.00000000);
INSERT INTO wallet_balances (user_id, asset, amount) VALUES (1, 'ETH', 0.00000000);