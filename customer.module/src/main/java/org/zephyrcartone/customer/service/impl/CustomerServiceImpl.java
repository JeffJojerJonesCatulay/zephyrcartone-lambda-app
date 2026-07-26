package org.zephyrcartone.customer.service.impl;

import org.zephyrcartone.customer.entity.CustomerEntity;
import org.zephyrcartone.customer.repository.CustomerRepository;
import org.zephyrcartone.customer.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
    CustomerRepository repository = new CustomerRepository();

    @Override
    public String saveCustomerData(CustomerEntity customer) {
        return repository.saveCustomer(customer);
    }
}
