package com.baila.badwallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BadWalletApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BadWalletApiApplication.class, args);
    }
}
