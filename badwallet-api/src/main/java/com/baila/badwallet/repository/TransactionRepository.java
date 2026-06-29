package com.baila.badwallet.repository;

import com.baila.badwallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Accès aux transactions.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByWalletPhoneNumberOrderByCreatedAtDesc(String phoneNumber);
}
