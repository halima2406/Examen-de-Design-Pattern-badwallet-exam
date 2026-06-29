package com.baila.badwallet.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Implémentation de la contrainte {@link ValidTelephone}.
 * Accepte les numéros sénégalais : +221 puis 70/75/76/77/78 et 7 chiffres.
 */
public class TelephoneValidator implements ConstraintValidator<ValidTelephone, String> {

    private static final String SENEGAL_PATTERN = "^\\+221(70|75|76|77|78)[0-9]{7}$";

    @Override
    public boolean isValid(String telephone, ConstraintValidatorContext context) {
        if (telephone == null || telephone.isBlank()) {
            return false;
        }
        return telephone.matches(SENEGAL_PATTERN);
    }
}
