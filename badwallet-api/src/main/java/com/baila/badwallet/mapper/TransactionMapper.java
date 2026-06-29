package com.baila.badwallet.mapper;

import com.baila.badwallet.dto.response.TransactionResponse;
import com.baila.badwallet.entity.Transaction;
import org.springframework.stereotype.Component;

/**
 * Transforme l'entité Transaction en DTO de réponse.
 */
@Component
public class TransactionMapper {

    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getReference(),
                transaction.getType().name(),
                transaction.getMontant(),
                transaction.getFrais(),
                transaction.getPaymentMethod() != null ? transaction.getPaymentMethod().name() : null,
                transaction.getStatut().name(),
                transaction.getContrepartie(),
                transaction.getDescription(),
                transaction.getCreatedAt());
    }
}
