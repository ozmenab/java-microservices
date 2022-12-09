package com.kodlamaio.filterservice.kafka;

import com.kodlamaio.common.events.filterService.BrandUpdatedEvent;
import com.kodlamaio.common.events.filterService.CarCreatedEvent;
import com.kodlamaio.common.events.filterService.CarDeletedEvent;
import com.kodlamaio.common.events.filterService.CarUpdateEvent;
import com.kodlamaio.common.util.mapping.ModelMapperService;
import com.kodlamaio.filterservice.business.abstracts.CarFilterService;
import com.kodlamaio.filterservice.entities.CarFilter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CarFilterConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarFilterConsumer.class);
    private final CarFilterService carFilterService;
    private final ModelMapperService modelMapperService;

    @KafkaListener(
            topics = "filter-created"
            , groupId = "filterCreate"
    )
    public void consume(CarCreatedEvent event) {
        CarFilter carFilter = modelMapperService.forRequest().map(event, CarFilter.class);
        carFilter.setCarId(UUID.randomUUID().toString());
        carFilterService.save(carFilter);
        LOGGER.info("Inventory created event consumed: {}", event);
    }

    @KafkaListener(
            topics = "filter-updated"
            , groupId = "filterUpdate"
    )
    public void consume(CarUpdateEvent event) {
        CarFilter filter = modelMapperService.forRequest().map(event, CarFilter.class);
        String id = carFilterService.getByCarId(event.getCarId()).getId();
        filter.setId(id);
        carFilterService.save(filter);
        LOGGER.info("Inventory updated event consumed: {}", event);
    }

    @KafkaListener(
            topics = "filter-deleted"
            , groupId = "filterDelete"
    )
    public void consume(CarDeletedEvent event) {
        carFilterService.deleteByCarId(event.getCarId());
        LOGGER.info("Inventory updated event consumed: {}", event);
    }

    @KafkaListener(
            topics = "filter-brand-updated"
            , groupId = "filterUpdateBrandName"
    )
    public void consume(BrandUpdatedEvent event) {
        List<CarFilter> carFilters = carFilterService.getByBrandId(event.getId());
        for(CarFilter filter:carFilters){
            filter.setBrandName(event.getName());
            filter.setId(filter.getId());
            carFilterService.save(filter);
        }
        LOGGER.info("Inventory brandName updated event consumed: {}", event);
    }
}
