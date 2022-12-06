package com.kodlamaio.rentalservice.config.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "paymentclient", url = "http://localhost:9010/")
public interface PaymentService {
    @RequestMapping(method = RequestMethod.POST, value = "payment-service/api/v1/payments/check")
    void checkIfPaymentSuccessful(
            @RequestParam String cardNumber,
            @RequestParam String fullName,
            @RequestParam String cardCvv,
            @RequestParam double price);
}