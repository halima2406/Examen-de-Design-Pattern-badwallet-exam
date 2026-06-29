package com.baila.badwallet.service;

import com.baila.badwallet.dto.request.PayBillRequest;
import com.baila.badwallet.dto.request.PayFacturesRequest;
import com.baila.badwallet.dto.response.PaymentReceiptResponse;

public interface BillPaymentService {

    PaymentReceiptResponse payCurrentBill(PayBillRequest request);

    PaymentReceiptResponse paySpecificFactures(PayFacturesRequest request);
}
