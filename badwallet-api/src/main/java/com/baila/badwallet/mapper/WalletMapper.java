package com.baila.badwallet.mapper;

import com.baila.badwallet.dto.response.BalanceResponse;
import com.baila.badwallet.dto.response.WalletResponse;
import com.baila.badwallet.entity.Wallet;
import org.springframework.stereotype.Component;

/**
 * Transforme l'entité Wallet en DTO de réponse. Garde les services concis.
 */
@Component
public class WalletMapper {

    public WalletResponse toResponse(Wallet wallet) {
        return new WalletResponse(
                wallet.getId(),
                wallet.getPhoneNumber(),
                wallet.getEmail(),
                wallet.getCode(),
                wallet.getBalance(),
                wallet.getCurrency().name(),
                wallet.getCreatedAt());
    }

    public BalanceResponse toBalanceResponse(Wallet wallet) {
        return new BalanceResponse(
                wallet.getPhoneNumber(),
                wallet.getBalance(),
                wallet.getCurrency().name());
    }
}
