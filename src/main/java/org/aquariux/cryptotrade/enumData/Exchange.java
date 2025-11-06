package org.aquariux.cryptotrade.enumData;

import lombok.RequiredArgsConstructor;
import org.aquariux.cryptotrade.dto.PriceDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Exchange {

    BINANCE("BTCUSDT") {
        @Override
        public List<PriceDto> convert(Object raw) {
            List<PriceDto> list = new ArrayList<>();

            List<Map<String, Object>> arr = (List<Map<String, Object>>) raw;
            return arr.stream()
                    .filter(m -> isSupported((String) m.get("symbol")))
                    .map(m -> PriceDto.builder()
                            .symbol((String) m.get("symbol"))
                            .bidPrice(new BigDecimal((String) m.get("bidPrice")))
                            .askPrice(new BigDecimal((String) m.get("askPrice")))
                            .bidQty(new BigDecimal((String) m.get("bidQty")))
                            .askQty(new BigDecimal((String) m.get("askQty")))
                            .source(name)
                            .build()
                    )
                    .collect(Collectors.toList());
        }
    },

    HUOBI("ETHUSDT") {
        @Override
        public List<PriceDto> convert(Object raw) {
            List<PriceDto> list = new ArrayList<>();
            if (raw == null) return list;

            Map<String, Object> map = (Map<String, Object>) raw;
            List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");

            if (data == null) return List.of();

            return data.stream()
                    .filter(m -> {
                        String s = ((String) m.get("symbol")).toUpperCase();
                        return "ETHUSDT".equals(s);
                    })
                    .map(m -> PriceDto.builder()
                            .symbol(((String) m.get("symbol")).toUpperCase())
                            .bidPrice(new BigDecimal(m.get("bid").toString()))
                            .askPrice(new BigDecimal(m.get("ask").toString()))
                            .bidQty(m.get("bidSize") != null ? new BigDecimal(m.get("bidSize").toString()) : BigDecimal.ZERO)
                            .askQty(m.get("askSize") != null ? new BigDecimal(m.get("askSize").toString()) : BigDecimal.ZERO)
                            .source(name)
                            .build()
                    )
                    .collect(Collectors.toList());
        }
    }

    ;

    protected final String name;

    public abstract List<PriceDto> convert(Object raw);

    protected boolean isSupported(String symbol) {
        if (symbol == null) return false;
        symbol = symbol.toUpperCase();
        return symbol.equals("BTCUSDT") || symbol.equals("ETHUSDT");
    }
}
