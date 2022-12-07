package com.kodlamaio.inventoryservice.kafka;

import com.kodlamaio.common.events.rentalService.RentalCreatedEvent;
import com.kodlamaio.common.events.rentalService.RentalUpdatedEvent;
import com.kodlamaio.inventoryservice.business.abstracts.CarService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RentalConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalConsumer.class);
    private CarService carService;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            ,groupId = "rentalCreate"
    )
    public void consume(RentalCreatedEvent createdEvent){
        LOGGER.info(String.format("Order event received in stock service => %s", createdEvent.toString()));
        carService.changeCarState(createdEvent.getCarId(),2);
        LOGGER.info("Car rented");
        // save the order event into the database
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            ,groupId = "rentalUpdate"
    )
    public void consume(RentalUpdatedEvent rentalUpdatedEvent){
        LOGGER.info(String.format("Order event received in stock service => %s", rentalUpdatedEvent.toString()));
        LOGGER.info("Car state changed");
        carService.changeCarState(rentalUpdatedEvent.getNewCarId(),2);
        carService.changeCarState(rentalUpdatedEvent.getOldCarId(),1);
        // save the order event into the database
    }
}
