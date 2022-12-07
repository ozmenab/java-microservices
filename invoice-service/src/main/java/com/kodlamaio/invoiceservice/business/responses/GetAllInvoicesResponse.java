package com.kodlamaio.invoiceservice.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllInvoicesResponse {
    private String id;
    private String fullName;
    private String brandName;
    private String modelName;
    private double dailyPrice;
    private int rentedForDays;
    private double totalPrice;
}
