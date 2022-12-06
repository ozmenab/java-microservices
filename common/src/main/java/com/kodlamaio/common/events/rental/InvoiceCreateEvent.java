package com.kodlamaio.common.events.rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCreateEvent {
    private String fullName;
    private String brandName;
    private String modelName;
    private double dailyPrice;
    private int rentedForDays;
    private double totalPrice;
}
