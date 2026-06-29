package com.baila.badwallet.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Contrainte de validation personnalisée : numéro de téléphone sénégalais
 * au format international (+221 suivi de 70/75/76/77/78 et 7 chiffres).
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TelephoneValidator.class)
public @interface ValidTelephone {

    String message() default "Numéro de téléphone sénégalais invalide (ex: +221770000003)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
