package com.kodlamaio.rentalservice.business.abstracts;

import com.kodlamaio.paymentservice.business.requests.CreatePaymentRequest;
import com.kodlamaio.rentalservice.business.requests.create.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.requests.update.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.responses.create.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.responses.get.GetAllRentalsResponse;
import com.kodlamaio.rentalservice.business.responses.get.GetRentalResponse;
import com.kodlamaio.rentalservice.business.responses.update.UpdateRentalResponse;

import java.util.List;

public interface RentalService {
    List<GetAllRentalsResponse> getAll();
    GetRentalResponse getById(String id);
    CreateRentalResponse add(CreateRentalRequest createRentalRequest, CreatePaymentRequest paymentRequest);
    UpdateRentalResponse update(String id, UpdateRentalRequest updateRentalRequest);
    void delete(String id);
}
