package com.sivalabs.bookstore.orders.services.rabitmqtest;

import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    //    @RabbitListener(queues = "${orders.new-orders-queue}")
    //    void handleNewOrder(MyPayload payload) {
    //        System.out.println("Consumed message from queue -> New Order: " + payload.content());
    //    }
    //
    //    @RabbitListener(queues = "${orders.delivered-orders-queue}")
    //    public void handleDeliveredOrder(MyPayload payload) {
    //        System.out.println("Consumed message from queue -> Delivered Order: " + payload.content());
    //    }
}
