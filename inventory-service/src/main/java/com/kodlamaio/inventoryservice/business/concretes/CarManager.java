package com.kodlamaio.inventoryservice.business.concretes;



import com.kodlamaio.common.dto.GetCarResponseDto;
import com.kodlamaio.common.events.filterService.CarCreatedEvent;
import com.kodlamaio.common.events.filterService.CarDeletedEvent;
import com.kodlamaio.common.events.filterService.CarUpdateEvent;
import com.kodlamaio.common.util.exceptions.BusinessException;
import com.kodlamaio.common.util.mapping.ModelMapperService;
import com.kodlamaio.inventoryservice.business.abstracts.CarService;
import com.kodlamaio.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryservice.entities.Car;
import com.kodlamaio.inventoryservice.kafka.FilterServiceProducer;
import com.kodlamaio.inventoryservice.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CarManager implements CarService {
    private CarRepository carRepository;
    private ModelMapperService modelMapperService;
    private FilterServiceProducer filterServiceProducer;

    @Override
    public List<GetAllCarsResponse> getAll() {
        List<Car> cars = carRepository.findAll();
        List<GetAllCarsResponse> response = cars
                .stream()
                .map(car -> modelMapperService.forResponse().map(car, GetAllCarsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetCarResponseDto getById(String id) {
        checkIfCarExistsById(id);
        Car car = carRepository.findById(id).orElseThrow();
        GetCarResponseDto response = modelMapperService.forResponse().map(car, GetCarResponseDto.class);
        return response;
    }

    @Override
    public CreateCarResponse add(CreateCarRequest request) {
        checkIfCarExistsByPlate(request.getPlate());
        Car car = modelMapperService.forRequest().map(request, Car.class);
        car.setId(UUID.randomUUID().toString());
        carRepository.save(car);
        CreateCarResponse response = modelMapperService.forResponse().map(car, CreateCarResponse.class);
        addToFilterService(response.getId());
        return response;
    }

    @Override
    public UpdateCarResponse update(UpdateCarRequest updateCarRequest, String id) {
        Car car = checkIfCarExistsById(id);
        Car updatedCar = modelMapperService.forRequest().map(updateCarRequest, Car.class);
        updatedCar.setId(id);
        updatedCar.setState(car.getState());
        carRepository.save(updatedCar);
        UpdateCarResponse response = modelMapperService.forResponse().map(car, UpdateCarResponse.class);
        updateToFilterService(id,updateCarRequest);
        return response;
    }

    @Override
    public void delete(String id) {
        checkIfCarExistsById(id);
        CarDeletedEvent event = new CarDeletedEvent();
        event.setCarId(id);
        filterServiceProducer.sendMessage(event);
        carRepository.deleteById(id);
    }

    @Override
    public void changeCarState(String id, int state) {
        carRepository.setCarStateById(id,state);
    }

    @Override
    public void carAvialibleState(String carId) {
        checkIfCarAvialible(carId);
    }

    private Car checkIfCarExistsById(String id) {
        Car car = carRepository.findById(id).orElse(null);
        if (car==null) {
            throw new BusinessException("CAR.NOT_EXISTS");
        }
        return car;
    }

    private void checkIfCarExistsByPlate(String plate) {
        if (carRepository.existsByPlateIgnoreCase(plate)) {
            throw new BusinessException("CAR.ALREADY_EXISTS");
        }
    }

    private void checkIfCarAvialible(String carId){
        Car car = carRepository.findById(carId).orElse(null);
        if(car == null || car.getState() != 1)
            throw new BusinessException("Car no exists or car no avialible");
    }

    private void addToFilterService(String id) {
        Car car = carRepository.findById(id).get();
        CarCreatedEvent event = modelMapperService.forResponse().map(car,CarCreatedEvent.class);
        filterServiceProducer.sendMessage(event);
    }

    private void updateToFilterService(String id,UpdateCarRequest updateCarRequest) {
        Car car = carRepository.findById(id).orElseThrow();
        car.getModel().setId(updateCarRequest.getModelId());
        car.getModel().getBrand().setId(car.getModel().getBrand().getId());
        car.setState(updateCarRequest.getState());
        car.setPlate(updateCarRequest.getPlate());
        car.setModelYear(updateCarRequest.getModelYear());
        car.setDailyPrice(updateCarRequest.getDailyPrice());
        CarUpdateEvent event = modelMapperService.forResponse().map(car,CarUpdateEvent.class);
        filterServiceProducer.sendMessage(event);
    }
}

