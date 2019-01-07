package com.rhyno.rx.ch3.order;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Order {
    private String buyer;
    private String name;
}