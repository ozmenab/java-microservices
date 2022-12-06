package com.kodlamaio.inventoryservice.business.dto.requests.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRequest {
    @Min(value = 0)
    private double dailyPrice;
    @Min(value = 2015)
    private int modelYear;
    @NotBlank
    @NotNull
    private String plate;
    @NotBlank
    @NotNull
    private String modelId;
}
