package org.zephyrcartone.ordering.service.impl;

import org.zephyrcartone.ordering.entity.OrderEntity;
import org.zephyrcartone.ordering.repository.OrderRepository;
import org.zephyrcartone.ordering.service.OrderService;

public class OrderServiceImpl implements OrderService {
    OrderRepository repository = new OrderRepository();

    @Override
    public String saveOrderData(OrderEntity order) {
        return repository.saveOrder(order);
    }
}
