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
import com.kodlamaio.paymentservice.business.responses.UpdatePaymentResponse;
import com.kodlamaio.paymentservice.entities.Payment;
import com.kodlamaio.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ModelMapperService modelMapperService;
    private final PosService posService;


    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        Payment payment = modelMapperService.forRequest().map(request, Payment.class);
        payment.setId(UUID.randomUUID().toString());
        paymentRepository.save(payment);
        CreatePaymentResponse response = modelMapperService.forResponse().map(payment, CreatePaymentResponse.class);
        return response;
    }

    @Override
    public void checkIfPaymentSuccessful(PaymentRequest request) {
        checkPayment(request);
    }

    @Override
    public List<GetAllPaymentsResponse> getAll() {
        List<Payment> payments = paymentRepository.findAll();
        List<GetAllPaymentsResponse> response = payments.stream()
                .map(payment -> modelMapperService
                        .forResponse().map(payment, GetAllPaymentsResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public void update(String id, UpdatePaymentRequest request) {
        Payment payment = paymentRepository.findById(id).orElseThrow(()->new BusinessException("Payment not found"));
        payment.setBalance(request.getBalance());
        payment.setId(id);
        paymentRepository.save(payment);
    }

    private void checkPayment(PaymentRequest request) {
        if (!paymentRepository.existsByCardNumberAndFullNameAndCardCvv(request.getCardNumber(), request.getFullName(), request.getCardCvv())) {
            throw new BusinessException("Invalid payment");
        }
        double balance = paymentRepository.findByCardNumber(request.getCardNumber()).getBalance();
        if (balance < request.getPrice()) {
            throw new BusinessException("No enough money");
        }
        posService.pay();
        Payment payment = paymentRepository.findByCardNumber(request.getCardNumber());
        payment.setBalance(balance - request.getPrice());
        paymentRepository.save(payment);
    }
}
