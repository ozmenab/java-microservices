package com.kodlamaio.paymentservice.business.concretes;

import com.kodlamaio.common.util.exceptions.BusinessException;
import com.kodlamaio.common.util.mapping.ModelMapperService;
import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.abstracts.PosService;
import com.kodlamaio.paymentservice.business.requests.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.requests.PaymentRequest;
import com.kodlamaio.paymentservice.business.requests.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.responses.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.responses.GetAllPaymentsResponse;
import com.kodlamaio.paymentservice.business.responses.GetPaymentResponse;
import com.kodlamaio.paymentservice.business.responses.UpdatePaymentResponse;
import com.kodlamaio.paymentservice.entities.Payment;
import com.kodlamaio.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
    private final PaymentRepository repository;
    private final ModelMapperService mapper;
    private final PosService posService;


    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        Payment payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(UUID.randomUUID().toString());
        repository.save(payment);
        CreatePaymentResponse response = mapper.forResponse().map(payment, CreatePaymentResponse.class);
        return response;
    }

    @Override
    public void checkIfPaymentSuccessful(PaymentRequest request) {
        checkPayment(request);
    }

    private void checkPayment(PaymentRequest request) {
        if (!repository.existsByCardNumberAndFullNameAndCardCvv(request.getCardNumber(), request.getFullName(), request.getCardCvv())) {
            throw new BusinessException("Invalid payment");
        }
        double balance = repository.findByCardNumber(request.getCardNumber()).getBalance();
        if (balance < request.getPrice()) {
            throw new BusinessException("No enough money");
        }
        posService.pay(); // Fake payment
        Payment payment = repository.findByCardNumber(request.getCardNumber());
        payment.setBalance(balance - request.getPrice());
        repository.save(payment);
    }
}
