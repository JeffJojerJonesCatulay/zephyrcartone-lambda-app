package org.zephyrcartone.ordering.service;

import org.zephyrcartone.ordering.entity.OrderEntity;

public interface OrderService {
    public String saveOrderData(OrderEntity order);
    public String getOrderData(String orderId);
}
