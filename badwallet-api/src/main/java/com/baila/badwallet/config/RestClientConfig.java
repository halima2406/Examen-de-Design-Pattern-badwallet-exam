package com.baila.badwallet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Configuration du client HTTP vers le service externe de facturation.
 * Le bean RestClient est un singleton géré par le conteneur Spring.
 */
@Configuration
public class RestClientConfig {

    @Bean
    public RestClient paymentServiceRestClient(
            @Value("${payment-service.base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
