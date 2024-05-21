package com.ferremas.ferremas.service;

import cl.transbank.common.IntegrationApiKeys;
import cl.transbank.common.IntegrationCommerceCodes;
import cl.transbank.common.IntegrationType;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.UUID;

@Service
public class TransbankService {
    private WebpayPlus.Transaction transaction;

    public TransbankService() {
        this.transaction = new WebpayPlus.Transaction(
                new WebpayOptions(
                        IntegrationCommerceCodes.WEBPAY_PLUS,
                        IntegrationApiKeys.WEBPAY,
                        IntegrationType.TEST
                )
        );
    }

    public WebpayPlusTransactionCreateResponse createTransaction(String buyOrder, double amount, String returnUrl) throws Exception {
        return transaction.create("pedido-ferremas-" + buyOrder, UUID.randomUUID().toString(), amount, returnUrl);
    }

    public WebpayPlusTransactionCommitResponse commitTransaction(String token) throws Exception {
        return transaction.commit(token);
    }
}
