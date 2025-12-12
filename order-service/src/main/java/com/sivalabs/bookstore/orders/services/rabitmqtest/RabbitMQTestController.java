package com.sivalabs.bookstore.orders.services.rabitmqtest;

import com.sivalabs.bookstore.orders.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQTestController {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQTestController.class);

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    RabbitMQTestController(RabbitTemplate rabbitTemplate, ApplicationProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody MyMessage message) {
        try {
            final RabbitTemplate rabbitTemplate1 = rabbitTemplate;
            rabbitTemplate1.convertAndSend(
                    properties.orderEventsExchange(), // Exchange name
                    message.routingKey(), // Routing key
                    message.payload()); // message/notification sent to rabbitmq Queue
        } catch (Exception e) {
            log.error("Exception occurred while sending message to RabbitMQ: ", e);
        }
    }
}
