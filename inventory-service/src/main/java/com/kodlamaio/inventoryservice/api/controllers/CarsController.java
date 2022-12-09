package com.kodlamaio.inventoryservice.api.controllers;

import com.kodlamaio.common.dto.GetCarResponseDto;
import com.kodlamaio.inventoryservice.business.abstracts.CarService;
import com.kodlamaio.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cars")
public class CarsController {
    private CarService carService;

    @GetMapping
    public List<GetAllCarsResponse> getAll() {
        return carService.getAll();
    }

    @PostMapping
    public CreateCarResponse add(@RequestBody CreateCarRequest request) {
        return carService.add(request);
    }

    @PutMapping("/{id}")
    public UpdateCarResponse update(@RequestBody UpdateCarRequest request, @PathVariable String id) {
        return carService.update(request, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        carService.delete(id);
    }

    @GetMapping("/{id}")
    public GetCarResponseDto getById(@PathVariable String id) {
        return carService.getById(id);
    }

    @GetMapping("/carAvialibleState/{carId}")
    public void carAvialibleState(@PathVariable String carId){
        carService.carAvialibleState(carId);
    }
}