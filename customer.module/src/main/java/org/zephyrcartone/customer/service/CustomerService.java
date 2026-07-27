package org.zephyrcartone.customer.service;

import org.zephyrcartone.customer.entity.CustomerEntity;

public interface CustomerService {
    public String saveCustomerData(CustomerEntity customer);
    public String getCustomerData(String customerId);
}
