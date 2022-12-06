package com.kodlamaio.rentalservice.business.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {
    //private LocalDate dateStarted;
    private String carId;
    private int rentedForDays;
    private double dailyPrice;
    private double totalPrice;
}
