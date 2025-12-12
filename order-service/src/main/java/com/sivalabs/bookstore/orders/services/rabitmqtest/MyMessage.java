package com.sivalabs.bookstore.orders.services.rabitmqtest;

public record MyMessage(String routingKey, MyPayload payload) {}
