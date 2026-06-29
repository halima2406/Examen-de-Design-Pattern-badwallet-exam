package com.baila.badwallet.pattern.strategy;

import com.baila.badwallet.entity.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

@Component
public class WithdrawalFeeStrategy implements FeeStrategy {

    private static final BigDecimal TAUX = new BigDecimal("0.01");
    private static final BigDecimal PLAFOND = new BigDecimal("5000");

    @Override
    public Set<TransactionType> appliesTo() {
        return Set.of(TransactionType.RETRAIT);
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        BigDecimal frais = amount.multiply(TAUX).setScale(2, RoundingMode.HALF_UP);
        return frais.min(PLAFOND);
    }
}
