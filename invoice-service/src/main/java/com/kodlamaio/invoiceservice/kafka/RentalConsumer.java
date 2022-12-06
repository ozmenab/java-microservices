package com.kodlamaio.invoiceservice.kafka;

import com.kodlamaio.common.events.rental.InvoiceCreateEvent;
import com.kodlamaio.common.util.mapping.ModelMapperService;
import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.entities.Invoice;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RentalConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalConsumer.class);
    private ModelMapperService modelMapperService;
    private InvoiceService invoiceService;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            ,groupId = "invoiceCreated"
    )
    public void consume(InvoiceCreateEvent createdEvent){
        LOGGER.info(String.format("Order event received in invoice-service => %s", createdEvent.toString()));
        Invoice invoice = modelMapperService.forRequest().map(createdEvent,Invoice.class);
        invoiceService.add(invoice);
        LOGGER.info("invoice created");
        // save the order event into the database
    }
}
