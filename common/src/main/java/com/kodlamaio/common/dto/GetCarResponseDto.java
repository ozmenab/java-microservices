package com.kodlamaio.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarResponseDto {
    private String id;
    private double dailyPrice;
    private int modelYear;
    private String plate;
    private int state;
    private String brandName;
    private String modelName;
}
