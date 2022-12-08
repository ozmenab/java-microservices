package com.kodlamaio.rentalservice.config.feign;


import com.kodlamaio.common.dto.GetCarResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import feign.Headers;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "inventoryservice",url = "http://localhost:9010/inventory-service")
public interface InventoryService {
    @RequestMapping(method = RequestMethod.GET,value = "/api/v1/cars/{carId}")
    @Headers(value = "Content-Type: application/json")
    GetCarResponseDto getCarById(@PathVariable String carId);
}
