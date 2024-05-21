package com.ferremas.ferremas.controller;

import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;
import com.ferremas.ferremas.dto.TransactionRequest;
import com.ferremas.ferremas.dto.TransactionResponse;
import com.ferremas.ferremas.service.TransbankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Random;
import java.util.random.RandomGenerator;

@RestController
@RequestMapping("/api/v1/transbank")
@RequiredArgsConstructor
public class TransbankController {
    private final TransbankService transbankService;

    @PostMapping("create")
    public TransactionResponse createTransaction(@RequestBody TransactionRequest transactionRequest) throws Exception {
        long seed = System.currentTimeMillis();
        Integer buyOrder = new Random(seed).nextInt();

        String returnUrl = "http://localhost:8080/pedido-completado.html";
        WebpayPlusTransactionCreateResponse response = transbankService.createTransaction(String.valueOf(buyOrder).substring(0, 9), transactionRequest.getAmount(), returnUrl);
        String url = response.getUrl() + "?token_ws=" + response.getToken();

        return new TransactionResponse(url);
    }

    @GetMapping("commit")
    public WebpayPlusTransactionCommitResponse commitTransaction(@RequestParam String token_ws) throws Exception {
        return transbankService.commitTransaction(token_ws);
    }
}
