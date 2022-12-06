package com.kodlamaio.paymentservice.business.abstracts;

import com.kodlamaio.paymentservice.business.requests.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.requests.PaymentRequest;
import com.kodlamaio.paymentservice.business.responses.CreatePaymentResponse;

public interface PaymentService {
    CreatePaymentResponse add(CreatePaymentRequest request);
    void checkIfPaymentSuccessful(PaymentRequest request);
}
