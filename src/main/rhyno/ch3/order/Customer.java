package com.rhyno.rx.ch3.order;

import lombok.Builder;

import java.util.List;

@Builder
public class Customer {
    private String name;
    private List<Order> orders;

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }
}