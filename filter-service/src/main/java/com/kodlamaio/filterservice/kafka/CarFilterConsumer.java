package com.kodlamaio.filterservice.kafka;

import com.kodlamaio.common.events.filterservice.CarCreatedEvent;
import com.kodlamaio.common.events.filterservice.CarUpdateEvent;
import com.kodlamaio.common.util.mapping.ModelMapperService;
import com.kodlamaio.filterservice.business.abstracts.CarFilterService;
import com.kodlamaio.filterservice.entities.CarFilter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CarFilterConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarFilterConsumer.class);
    private final CarFilterService carFilterService;
    private final ModelMapperService modelMapperService;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            , groupId = "inventory-create"
    )
    public void consume(CarCreatedEvent event) {
        CarFilter carFilter = modelMapperService.forRequest().map(event, CarFilter.class);
        carFilter.setId(UUID.randomUUID().toString());
        carFilterService.save(carFilter);
        LOGGER.info("Inventory created event consumed: {}", event);
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            , groupId = "inventory-update"
    )
    public void consume(CarUpdateEvent event) {
        CarFilter filter = modelMapperService.forRequest().map(event, CarFilter.class);
        String id = carFilterService.getByCarId(event.getCarId()).getId();
        filter.setId(id);
        carFilterService.save(filter);
        LOGGER.info("Inventory updated event consumed: {}", event);
    }
}
