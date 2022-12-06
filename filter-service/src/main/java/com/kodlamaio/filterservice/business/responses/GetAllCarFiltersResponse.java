package com.kodlamaio.filterservice.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCarFiltersResponse {
    private String id;
    private String carId;
    private String brandId;
    private String brandName;
    private String modelId;
    private String modelName;
    private int modelYear;
    private double dailyPrice;
    private String plate;
    private int state;
}
