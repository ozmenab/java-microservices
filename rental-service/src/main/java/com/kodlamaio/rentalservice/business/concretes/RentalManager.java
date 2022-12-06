package com.kodlamaio.rentalservice.business.concretes;

import com.kodlamaio.common.events.rental.InvoiceCreateEvent;
import com.kodlamaio.common.events.rental.RentalUpdatedEvent;
import com.kodlamaio.paymentservice.business.requests.CreatePaymentRequest;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.requests.create.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.requests.update.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.responses.create.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.responses.get.GetAllRentalsResponse;
import com.kodlamaio.rentalservice.business.responses.get.GetRentalResponse;
import com.kodlamaio.rentalservice.business.responses.update.UpdateRentalResponse;
import com.kodlamaio.rentalservice.config.feign.InventoryService;
import com.kodlamaio.rentalservice.config.feign.PaymentService;
import com.kodlamaio.rentalservice.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentalservice.entities.Rental;
import com.kodlamaio.rentalservice.kafka.RentalProducer;
import com.kodlamaio.common.events.rental.RentalCreatedEvent;
import com.kodlamaio.common.util.exceptions.BusinessException;
import com.kodlamaio.common.util.mapping.ModelMapperService;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetCarResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
    private RentalRepository rentalRepository;
    private ModelMapperService modelMapperService;
    private RentalProducer rentalProducer;
    private InventoryService inventoryService;
    private PaymentService paymentService;

    @Override
    public List<GetAllRentalsResponse> getAll() {
        List<Rental> rentals = rentalRepository.findAll();
        List<GetAllRentalsResponse> response = rentals
                .stream().map(rental -> modelMapperService.forResponse().map(rental, GetAllRentalsResponse.class))
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public GetRentalResponse getById(String id) {
        return null;
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest createRentalRequest, CreatePaymentRequest paymentRequest) {
        checkIfCarAvialible(createRentalRequest.getCarId());
        Rental rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);
        rental.setId(UUID.randomUUID().toString());
        rental.setTotalPrice(rental.getDailyPrice() * rental.getRentedForDays());

        paymentService.checkIfPaymentSuccessful(paymentRequest.getCardNumber(),
                paymentRequest.getFullName(), paymentRequest.getCardCvv(), rental.getTotalPrice());

        Rental rentalCreated = rentalRepository.save(rental);
        RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
        rentalCreatedEvent.setCarId(rentalCreated.getCarId());
        rentalCreatedEvent.setMessage("Rental Created");
        rentalProducer.sendMessage(rentalCreatedEvent);

        createInvoiceProducer(rental,paymentRequest);

        CreateRentalResponse response = modelMapperService.forResponse().map(rental, CreateRentalResponse.class);
        return response;
    }


    @Override
    public UpdateRentalResponse update(String id, UpdateRentalRequest updateRentalRequest) {
        Rental rentalrequest = checkIfRentalExistsById(id);
        checkIfCarAvialible(updateRentalRequest.getCarId());
        RentalUpdatedEvent rentalUpdatedEvent = new RentalUpdatedEvent();
        Rental rental = modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
        rental.setId(id);
        rentalUpdatedEvent.setOldCarId(rentalrequest.getCarId());
        rentalRepository.save(rental);
        rentalUpdatedEvent.setNewCarId(rental.getCarId());
        rentalUpdatedEvent.setMessage("Rental Updated");
        rentalProducer.sendMessage(rentalUpdatedEvent);
        UpdateRentalResponse response = modelMapperService.forResponse().map(rental, UpdateRentalResponse.class);
        return response;
    }

    @Override
    public void delete(String id) {

    }

    private void createInvoiceProducer(Rental rental, CreatePaymentRequest paymentRequest) {
        InvoiceCreateEvent invoiceCreateEvent = new InvoiceCreateEvent();
        GetCarResponse car = inventoryService.chekIfCarAvialible(rental.getCarId());
        invoiceCreateEvent.setBrandName(car.getBrandName());
        invoiceCreateEvent.setModelName(car.getModelName());
        invoiceCreateEvent.setTotalPrice(rental.getTotalPrice());
        invoiceCreateEvent.setFullName(paymentRequest.getFullName());
        invoiceCreateEvent.setDailyPrice(rental.getDailyPrice());
        invoiceCreateEvent.setRentedForDays(rental.getRentedForDays());
        rentalProducer.sendMessage(invoiceCreateEvent);
    }

    private void checkIfCarAvialible(String carId) {
        GetCarResponse carResponse = inventoryService.chekIfCarAvialible(carId);
        if (carResponse.getState() != 1)
            throw new BusinessException("Car no avialible");
    }

    private Rental checkIfRentalExistsById(String id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Rental no exists"));
        return rental;
    }
}
