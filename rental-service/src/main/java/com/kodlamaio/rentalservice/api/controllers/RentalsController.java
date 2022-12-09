package com.kodlamaio.rentalservice.api.controllers;

import com.kodlamaio.common.dto.CreatePaymentRequest;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.requests.create.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.requests.update.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.responses.create.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.responses.get.GetAllRentalsResponse;
import com.kodlamaio.rentalservice.business.responses.get.GetRentalResponse;
import com.kodlamaio.rentalservice.business.responses.update.UpdateRentalResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rentals")
@AllArgsConstructor
public class RentalsController {
    private RentalService rentalService;

    @GetMapping
    public List<GetAllRentalsResponse> getAll(){
        return rentalService.getAll();
    }

    @PostMapping
    public CreateRentalResponse add(@RequestBody CreateRentalRequest createRentalRequest,
                                    @RequestParam String cardNumber,
                                    @RequestParam String fullName,
                                    @RequestParam String cardCvv){
        CreatePaymentRequest paymentRequest = new CreatePaymentRequest();
        paymentRequest.setCardNumber(cardNumber);
        paymentRequest.setFullName(fullName);
        paymentRequest.setCardCvv(cardCvv);
        return rentalService.add(createRentalRequest,paymentRequest);
    }

    @PutMapping("/{id}")
    public UpdateRentalResponse update(@PathVariable String id,@RequestBody UpdateRentalRequest updateRentalRequest){
        return rentalService.update(id,updateRentalRequest);
    }

    @GetMapping("/{id}")
    public GetRentalResponse getById(@PathVariable String id){
        return rentalService.getById(id);
    }
}
