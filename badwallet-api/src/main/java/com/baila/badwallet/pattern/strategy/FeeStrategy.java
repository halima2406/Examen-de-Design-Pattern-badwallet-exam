package com.baila.badwallet.pattern.strategy;

import com.baila.badwallet.entity.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Pattern STRATEGY.
 *
 * Abstraction du calcul des frais. Chaque implémentation encapsule une règle
 * de tarification interchangeable. Ajouter une nouvelle règle = ajouter une
 * implémentation, sans modifier le code existant (principe Ouvert/Fermé - SOLID).
 */
public interface FeeStrategy {

    /** Types d'opérations couverts par cette stratégie. */
    Set<TransactionType> appliesTo();

    /** Calcule les frais appliqués pour le montant donné. */
    BigDecimal calculateFee(BigDecimal amount);
}
