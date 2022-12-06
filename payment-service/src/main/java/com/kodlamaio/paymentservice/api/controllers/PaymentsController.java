package com.kodlamaio.paymentservice.api.controllers;

import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.requests.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.requests.PaymentRequest;
import com.kodlamaio.paymentservice.business.responses.CreatePaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentsController {
    private final PaymentService service;

    @PostMapping
    public CreatePaymentResponse add(@RequestBody CreatePaymentRequest request) {
        return service.add(request);
    }

    @PostMapping("/check")
    public void checkIfPaymentSuccessful(@RequestParam String cardNumber, @RequestParam String fullName,
             @RequestParam String cardCvv, @RequestParam double price) {
        PaymentRequest request = new PaymentRequest(cardNumber, fullName, cardCvv, price);
        service.checkIfPaymentSuccessful(request);
    }
}
