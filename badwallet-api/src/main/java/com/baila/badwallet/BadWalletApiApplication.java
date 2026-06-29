package com.baila.badwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Point d'entrée de l'API BadWallet (portefeuilles électroniques).
 * {@code @EnableAsync} active le seeding asynchrone de la base.
 */
@EnableAsync
@SpringBootApplication
public class BadWalletApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BadWalletApiApplication.class, args);
    }
}
