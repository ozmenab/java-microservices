package com.kodlamaio.filterservice.business.concretes;

import com.kodlamaio.common.events.filterService.CarCreatedEvent;
import com.kodlamaio.common.util.mapping.ModelMapperService;
import com.kodlamaio.filterservice.business.abstracts.CarFilterService;
import com.kodlamaio.filterservice.business.responses.GetAllCarFiltersResponse;
import com.kodlamaio.filterservice.entities.CarFilter;
import com.kodlamaio.filterservice.repository.CarFilterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarFilterManager implements CarFilterService {
    private CarFilterRepository carFilterRepository;
    private ModelMapperService modelMapperService;

    @Override
    public void save(CarFilter carFilter) {
        carFilterRepository.save(carFilter);
    }

    @Override
    public void delete(String id) {
        carFilterRepository.deleteById(id);
    }

    @Override
    public List<GetAllCarFiltersResponse> getAll() {
        List<CarFilter> cars = carFilterRepository.findAll();
        List<GetAllCarFiltersResponse> response = cars
                .stream().map(carFilter -> modelMapperService.forResponse().map(carFilter,GetAllCarFiltersResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public List<GetAllCarFiltersResponse> getByBrandName(String brandName) {
        List<CarFilter> filters = carFilterRepository.findByBrandNameContainingIgnoreCase(brandName);
        List<GetAllCarFiltersResponse> response = filters
                .stream()
                .map(filter -> modelMapperService.forResponse().map(filter, GetAllCarFiltersResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public List<GetAllCarFiltersResponse> getByModelName(String modelName) {
        List<CarFilter> filters = carFilterRepository.findByModelNameContainingIgnoreCase(modelName);
        List<GetAllCarFiltersResponse> response = filters
                .stream()
                .map(filter -> modelMapperService.forResponse().map(filter, GetAllCarFiltersResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public CarFilter getByCarId(String id) {
        return carFilterRepository.findByCarId(id);
    }

    @Override
    public List<CarFilter> getByModelId(String modelId) {
        return carFilterRepository.findAllByModelId(modelId);
    }

    @Override
    public List<CarFilter> getByBrandId(String brandId) {
        return carFilterRepository.findAllByBrandId(brandId);
    }

    @Override
    public void deleteByCarId(String id) {
        carFilterRepository.deleteByCarId(id);
    }

    @Override
    public List<CarFilter> getByBrandNameOrModelName(String name) {
        List<CarFilter> filters = carFilterRepository.findAllByModelNameIgnoreCaseOrBrandNameIgnoreCase(name);
        return filters;
    }
}
