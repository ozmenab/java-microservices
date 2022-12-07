package com.kodlamaio.filterservice.api.controllers;

import com.kodlamaio.filterservice.business.abstracts.CarFilterService;
import com.kodlamaio.filterservice.business.responses.GetAllCarFiltersResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
