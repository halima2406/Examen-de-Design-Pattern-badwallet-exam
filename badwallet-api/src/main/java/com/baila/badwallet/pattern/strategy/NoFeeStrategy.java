package com.baila.badwallet.pattern.strategy;

import com.baila.badwallet.entity.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class NoFeeStrategy implements FeeStrategy {

    @Override
    public Set<TransactionType> appliesTo() {
        return Set.of(
                TransactionType.DEPOT,
                TransactionType.TRANSFERT_ENVOYE,
                TransactionType.TRANSFERT_RECU,
                TransactionType.PAIEMENT_FACTURE);
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
