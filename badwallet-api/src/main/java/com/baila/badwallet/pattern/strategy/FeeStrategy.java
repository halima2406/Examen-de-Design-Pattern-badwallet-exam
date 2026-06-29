package com.baila.badwallet.pattern.strategy;

import com.baila.badwallet.entity.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Set;

public interface FeeStrategy {

    Set<TransactionType> appliesTo();

    BigDecimal calculateFee(BigDecimal amount);
}
