package com.baila.badwallet.repository;

import com.baila.badwallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Accès aux portefeuilles. Requêtes dérivées générées par Spring Data JPA.
 */
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByPhoneNumber(String phoneNumber);

    Optional<Wallet> findByCode(String code);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByCode(String code);

    boolean existsByEmail(String email);
}
