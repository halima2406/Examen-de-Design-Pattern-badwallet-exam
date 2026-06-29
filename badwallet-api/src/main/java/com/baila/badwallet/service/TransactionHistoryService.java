package com.baila.badwallet.service;

import com.baila.badwallet.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionHistoryService {

    List<TransactionResponse> getHistory(String phoneNumber);
}
