package org.zephyrcartone.customer.controller;

import org.zephyrcartone.customer.entity.CustomerEntity;
import org.zephyrcartone.customer.service.impl.CustomerServiceImpl;

public class CustomerController {
    CustomerServiceImpl service = new CustomerServiceImpl();

    public String createCustomer(CustomerEntity customer){
        return service.saveCustomerData(customer);
    }

    public String getCustomer(CustomerEntity customer){
        return service.getCustomerData(customer.getCustomerId());
    }
}
