package com.kodlamaio.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {
    private String cardNumber;
    private String fullName;
    private String cardCvv;
    private double balance;
}
