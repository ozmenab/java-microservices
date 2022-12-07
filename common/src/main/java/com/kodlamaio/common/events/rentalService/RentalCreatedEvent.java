package com.kodlamaio.common.events.rentalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalCreatedEvent {
    private String message;
    private String carId;
}
