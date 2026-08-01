package org.zephyrcartone.ordering.controller;

import org.zephyrcartone.ordering.entity.OrderEntity;
import org.zephyrcartone.ordering.service.impl.OrderServiceImpl;

public class OrderController {
    OrderServiceImpl service = new OrderServiceImpl();

    public String createOrder(OrderEntity order){
        return service.saveOrderData(order);
    }

    public String getOrder(OrderEntity order){
        return service.getOrderData(order.getOrderId());
    }
}
