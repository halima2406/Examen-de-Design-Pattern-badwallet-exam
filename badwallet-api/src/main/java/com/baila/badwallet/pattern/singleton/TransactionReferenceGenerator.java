package com.baila.badwallet.pattern.singleton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public final class TransactionReferenceGenerator {

    private static volatile TransactionReferenceGenerator instance;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String PREFIX = "TRX";

    private final AtomicLong sequence = new AtomicLong(0);

    private TransactionReferenceGenerator() {

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

    public String next() {
        long value = sequence.incrementAndGet();
        return String.format("%s-%s-%010d", PREFIX, LocalDate.now().format(DATE_FORMAT), value);
    }
}
