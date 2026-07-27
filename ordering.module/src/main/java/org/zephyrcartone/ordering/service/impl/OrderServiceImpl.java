package org.zephyrcartone.ordering.service.impl;

import org.zephyrcartone.ordering.entity.OrderEntity;
import org.zephyrcartone.ordering.repository.OrderRepository;
import org.zephyrcartone.ordering.service.OrderService;
import org.zephyrcartone.ordering.utility.Constant;
import org.zephyrcartone.ordering.utility.Validations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    OrderRepository repository = new OrderRepository();

    @Override
    public String saveOrderData(OrderEntity order) {
        if (order.getOrderId() != null){
            throw new RuntimeException(Constant.PARTITION_KEY_PROVIDED_ERR_MESSAGE);
        }

        Validations validations = new Validations();
        // Validate mandatory fields
        validations.validateMandatoryField(order.getCustomerId());
        validations.validateMandatoryField(order.getItemId());
        validations.validateMandatoryField(order.getStatus());

        // Filter characters not allowed
        validations.validateString(order.getStatus());

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        order.setCreationDate(dateTime.format(formatter));

        String orderId = UUID.randomUUID().toString();
        order.setOrderId(orderId);

        return repository.saveOrder(order);
    }
}
