package com.kodlamaio.filterservice.business.abstracts;

import com.kodlamaio.filterservice.business.responses.GetAllCarFiltersResponse;
import com.kodlamaio.filterservice.entities.CarFilter;

import java.util.List;

public interface CarFilterService {
    void save(CarFilter carFilter);
    void delete(String id);
    List<GetAllCarFiltersResponse> getAll();
    List<GetAllCarFiltersResponse> getByBrandName(String brandName);
    List<GetAllCarFiltersResponse> getByModelName(String modelName);
    CarFilter getByCarId(String id);
    List<CarFilter> getByModelId(String modelId);
    List<CarFilter> getByBrandId(String brandId);
    void deleteByCarId(String id);
    List<CarFilter> getByBrandNameOrModelName(String name);
}
