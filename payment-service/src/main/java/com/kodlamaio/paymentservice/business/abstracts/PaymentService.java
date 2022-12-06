package com.kodlamaio.paymentservice.business.abstracts;

import com.kodlamaio.paymentservice.business.requests.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.requests.PaymentRequest;
import com.kodlamaio.paymentservice.business.requests.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.responses.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.responses.GetAllPaymentsResponse;
import com.kodlamaio.paymentservice.business.responses.UpdatePaymentResponse;

import java.util.List;

public interface PaymentService {
    CreatePaymentResponse add(CreatePaymentRequest request);
    void checkIfPaymentSuccessful(PaymentRequest request);
    List<GetAllPaymentsResponse> getAll();
    void update(String id,UpdatePaymentRequest updatePaymentRequest);
}
