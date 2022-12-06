package com.kodlamaio.paymentservice.business.responses;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentResponse {
    private String id;
    private String cardNumber;
    private String fullName;
    private String cardCvv;
    private double balance;
}