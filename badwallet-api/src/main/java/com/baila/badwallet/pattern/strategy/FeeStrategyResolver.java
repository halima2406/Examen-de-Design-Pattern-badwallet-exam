package com.baila.badwallet.pattern.strategy;

import com.baila.badwallet.entity.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class FeeStrategyResolver {

    private final Map<TransactionType, FeeStrategy> strategiesParType = new EnumMap<>(TransactionType.class);

    public FeeStrategyResolver(List<FeeStrategy> strategies) {
        for (FeeStrategy strategy : strategies) {
            for (TransactionType type : strategy.appliesTo()) {
                strategiesParType.put(type, strategy);
            }
        }
    }

    public BigDecimal resolveFee(TransactionType type, BigDecimal amount) {
        FeeStrategy strategy = strategiesParType.get(type);
        if (strategy == null) {
            return BigDecimal.ZERO;
        }
        return strategy.calculateFee(amount);
    }
}
