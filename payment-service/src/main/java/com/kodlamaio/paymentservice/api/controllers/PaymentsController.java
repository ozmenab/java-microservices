package com.kodlamaio.paymentservice.api.controllers;

import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.requests.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.requests.PaymentRequest;
import com.kodlamaio.paymentservice.business.requests.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.responses.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.responses.GetAllPaymentsResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentsController {
    private final PaymentService paymentService;

    @PostMapping
    public CreatePaymentResponse add(@RequestBody CreatePaymentRequest request) {
        return paymentService.add(request);
    }

    @PostMapping("/check")
    public void checkIfPaymentSuccessful(@RequestParam String cardNumber, @RequestParam String fullName,
             @RequestParam String cardCvv, @RequestParam double price) {
        PaymentRequest request = new PaymentRequest(cardNumber, fullName, cardCvv, price);
        paymentService.checkIfPaymentSuccessful(request);
    }

    @PutMapping("/{id}")
    public void setBalance(@PathVariable String id, @RequestBody UpdatePaymentRequest updatePaymentRequest){
        paymentService.update(id,updatePaymentRequest);
    }

    @GetMapping
    public List<GetAllPaymentsResponse> getAll(){
        return paymentService.getAll();
    }
}
