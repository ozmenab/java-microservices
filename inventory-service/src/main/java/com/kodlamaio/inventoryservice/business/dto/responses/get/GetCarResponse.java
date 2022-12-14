package com.kodlamaio.inventoryservice.business.dto.responses.get;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCarResponse {
    private String id;
    private double dailyPrice;
    private int modelYear;
    private String plate;
    private int state;
    private String brandName;
    private String modelName;
}
