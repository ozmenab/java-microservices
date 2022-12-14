package com.kodlamaio.rentalservice.kafka;

import com.kodlamaio.common.events.rentalService.InvoiceCreateEvent;
import com.kodlamaio.common.events.rentalService.RentalCreatedEvent;
import com.kodlamaio.common.events.rentalService.RentalUpdatedEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RentalProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalProducer.class);
    private NewTopic topic;
    private KafkaTemplate<String, Object> kafkaTemplate;

    public RentalProducer(NewTopic topic, KafkaTemplate<String, Object> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(RentalCreatedEvent rentalCreatedEvent) {
        LOGGER.info(String.format("Rental created event => %s", rentalCreatedEvent.toString()));

        Message<RentalCreatedEvent> message = MessageBuilder
                .withPayload(rentalCreatedEvent)
                .setHeader(KafkaHeaders.TOPIC, "rental-created").build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(RentalUpdatedEvent rentalUpdatedEvent) {
        LOGGER.info(String.format("Rental updated event => %s", rentalUpdatedEvent.toString()));

        Message<RentalUpdatedEvent> message = MessageBuilder
                .withPayload(rentalUpdatedEvent)
                .setHeader(KafkaHeaders.TOPIC, "rental-updated").build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(InvoiceCreateEvent invoiceCreateEvent) {
        LOGGER.info(String.format("Rental invoice event => %s", invoiceCreateEvent.toString()));

        Message<InvoiceCreateEvent> message = MessageBuilder
                .withPayload(invoiceCreateEvent)
                .setHeader(KafkaHeaders.TOPIC, "invoice-created").build();

        kafkaTemplate.send(message);
    }
}
