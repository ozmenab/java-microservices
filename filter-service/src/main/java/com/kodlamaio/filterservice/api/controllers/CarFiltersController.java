package com.kodlamaio.filterservice.api.controllers;

import com.kodlamaio.filterservice.business.abstracts.CarFilterService;
import com.kodlamaio.filterservice.business.responses.GetAllCarFiltersResponse;
import com.kodlamaio.filterservice.entities.CarFilter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carFilters")
@AllArgsConstructor
public class CarFiltersController {
    private CarFilterService carFilterService;

    @GetMapping
    public List<GetAllCarFiltersResponse> getAll(){
        return carFilterService.getAll();
    }

    @GetMapping("/getByBrandName")
    public List<GetAllCarFiltersResponse> getByBrandName(@RequestParam String brandName){
        return carFilterService.getByBrandName(brandName);
    }

    @GetMapping("/getByModelName")
    public List<GetAllCarFiltersResponse> getByModelName(@RequestParam String modelName){
        return carFilterService.getByModelName(modelName);
    }

    @GetMapping("/getByCarId/{id}")
    public CarFilter getByCarId(@PathVariable String id) {
        return carFilterService.getByCarId(id);
    }

    @GetMapping("/getByModelId/{id}")
    public List<CarFilter> getByModelId(@PathVariable String modelId){
        return carFilterService.getByModelId(modelId);
    }

    @GetMapping("/getByBrandId/{id}")
    public List<CarFilter> getByBrandId(@PathVariable String brandId){
        return carFilterService.getByBrandId(brandId);
    }
}
