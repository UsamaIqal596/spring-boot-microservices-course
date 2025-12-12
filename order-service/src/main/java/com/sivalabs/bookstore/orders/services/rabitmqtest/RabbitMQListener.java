package com.sivalabs.bookstore.orders.services.rabitmqtest;

import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {
    // we need to uncomment them just for testing as otherwise they will consume all the messages
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
