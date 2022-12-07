package com.kodlamaio.filterservice.repository;

import com.kodlamaio.filterservice.entities.CarFilter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CarFilterRepository extends MongoRepository<CarFilter,String> {
    List<CarFilter> findByBrandNameContainingIgnoreCase(String brandName);
    List<CarFilter> findByModelNameContainingIgnoreCase(String brandName);
    CarFilter findByCarId(String carId);
    CarFilter findByModelId(String modelId);
    List<CarFilter> findAllByModelId(String modelId);
    List<CarFilter> findAllByBrandId(String modelId);
    void deleteByCarId(String carId);

    List<CarFilter> findAllByModelNameOrBrandName(String name);
}
