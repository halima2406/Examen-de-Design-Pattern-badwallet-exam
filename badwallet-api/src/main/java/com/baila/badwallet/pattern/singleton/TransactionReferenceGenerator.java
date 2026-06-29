package com.baila.badwallet.pattern.singleton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Pattern SINGLETON (GoF).
 *
 * Générateur unique de références de transactions. On veut une SEULE instance
 * partagée dans toute l'application pour garantir l'unicité de la séquence,
 * d'où le constructeur privé et l'accès via {@link #getInstance()}.
 *
 * Implémentation thread-safe par "double-checked locking" + {@link AtomicLong}
 * pour le compteur. (Spring gère aussi des singletons via son conteneur, mais on
 * illustre ici le pattern GoF de façon explicite, indépendamment du framework.)
 *
 * Exemple de référence produite : TRX-20260629-0000000001
 */
public final class TransactionReferenceGenerator {

    private static volatile TransactionReferenceGenerator instance;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String PREFIX = "TRX";

    private final AtomicLong sequence = new AtomicLong(0);

    private TransactionReferenceGenerator() {
        // Empêche l'instanciation externe.
    }

    public static TransactionReferenceGenerator getInstance() {
        if (instance == null) {
            synchronized (TransactionReferenceGenerator.class) {
                if (instance == null) {
                    instance = new TransactionReferenceGenerator();
                }
            }
        }
        return instance;
    }

    /**
     * @return une référence de transaction unique et croissante.
     */
    public String next() {
        long value = sequence.incrementAndGet();
        return String.format("%s-%s-%010d", PREFIX, LocalDate.now().format(DATE_FORMAT), value);
    }
}
