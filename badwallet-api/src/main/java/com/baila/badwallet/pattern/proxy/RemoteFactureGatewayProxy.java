package com.baila.badwallet.pattern.proxy;

import com.baila.badwallet.dto.response.FactureResponse;
import com.baila.badwallet.exception.BusinessException;
import com.baila.badwallet.exception.RemoteServiceException;
import com.baila.badwallet.exception.ResourceNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class RemoteFactureGatewayProxy implements FactureGateway {

    private final RestClient restClient;

    public RemoteFactureGatewayProxy(RestClient paymentServiceRestClient) {
        this.restClient = paymentServiceRestClient;
    }

    @Override
    public List<FactureResponse> getFacturesDuMois(String walletCode, String unite) {
        try {
            RemoteApiResponse<List<FactureResponse>> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/factures/{walletCode}/current")
                            .queryParamIfPresent("unite",
                                    (unite == null || unite.isBlank()) ? java.util.Optional.empty()
                                            : java.util.Optional.of(unite))
                            .build(walletCode))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::traduireErreur)
                    .body(new ParameterizedTypeReference<>() {
                    });
            return extraireData(response);
        } catch (RestClientException ex) {
            throw new RemoteServiceException(
                    "Impossible de joindre le service de facturation (payment-service)", ex);
        }
    }

    @Override
    public List<FactureResponse> getFacturesSurPeriode(String walletCode, LocalDate debut, LocalDate fin) {
        try {
            RemoteApiResponse<List<FactureResponse>> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/factures/{walletCode}/periode")
                            .queryParam("debut", debut)
                            .queryParam("fin", fin)
                            .build(walletCode))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::traduireErreur)
                    .body(new ParameterizedTypeReference<>() {
                    });
            return extraireData(response);
        } catch (RestClientException ex) {
            throw new RemoteServiceException(
                    "Impossible de joindre le service de facturation (payment-service)", ex);
        }
    }

    @Override
    public FacturePaiementResultat payerFacturesDuMois(String walletCode, String serviceName) {
        try {
            RemoteApiResponse<FacturePaiementResultat> response = restClient.post()
                    .uri("/api/factures/{walletCode}/pay-current", walletCode)
                    .body(Map.of("serviceName", serviceName))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::traduireErreur)
                    .body(new ParameterizedTypeReference<>() {
                    });
            return extraireData(response);
        } catch (RestClientException ex) {
            throw new RemoteServiceException(
                    "Impossible de joindre le service de facturation (payment-service)", ex);
        }
    }

    @Override
    public FacturePaiementResultat payerFactures(String walletCode, List<String> references) {
        try {
            RemoteApiResponse<FacturePaiementResultat> response = restClient.post()
                    .uri("/api/factures/{walletCode}/pay", walletCode)
                    .body(Map.of("factureReferences", references))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, this::traduireErreur)
                    .body(new ParameterizedTypeReference<>() {
                    });
            return extraireData(response);
        } catch (RestClientException ex) {
            throw new RemoteServiceException(
                    "Impossible de joindre le service de facturation (payment-service)", ex);
        }
    }

    private <T> T extraireData(RemoteApiResponse<T> response) {
        if (response == null || !response.success()) {
            throw new RemoteServiceException(
                    response == null ? "Réponse vide du service de facturation" : response.message());
        }
        return response.data();
    }

    private void traduireErreur(org.springframework.http.HttpRequest request,
                                org.springframework.http.client.ClientHttpResponse response) throws java.io.IOException {
        HttpStatusCode statut = response.getStatusCode();
        String corps = new String(response.getBody().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        if (statut.value() == 404) {
            throw new ResourceNotFoundException("Ressource introuvable côté payment-service: " + corps);
        }
        if (statut.is4xxClientError()) {
            throw new BusinessException("Requête refusée par payment-service: " + corps);
        }
        throw new RemoteServiceException("Erreur du service de facturation (" + statut + ")");
    }
}
