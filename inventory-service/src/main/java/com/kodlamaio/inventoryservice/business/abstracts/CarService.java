package com.kodlamaio.inventoryservice.business.abstracts;

import com.kodlamaio.common.dto.GetCarResponseDto;
import com.kodlamaio.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetCarResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryservice.business.dto.requests.update.UpdateCarRequest;

import java.util.List;

public interface CarService {
    List<GetAllCarsResponse> getAll();
    GetCarResponseDto getById(String id);
    CreateCarResponse add(CreateCarRequest request);
    UpdateCarResponse update(UpdateCarRequest request, String id);
    void delete(String id);
    void changeCarState(String id,int state);

    void carAvialibleState(String carId);
}

