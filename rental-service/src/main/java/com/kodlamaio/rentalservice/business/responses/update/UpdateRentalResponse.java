package com.kodlamaio.rentalservice.business.responses.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalResponse {
    private String id;
    private LocalDate dateStarted;
    private String carId;
    private int rentedForDays;
    private double dailyPrice;
    private double totalPrice;
}
