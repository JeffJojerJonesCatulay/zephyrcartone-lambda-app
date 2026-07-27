package org.zephyrcartone.customer.service.impl;

import org.zephyrcartone.customer.entity.CustomerEntity;
import org.zephyrcartone.customer.repository.CustomerRepository;
import org.zephyrcartone.customer.service.CustomerService;
import org.zephyrcartone.customer.utility.Constant;
import org.zephyrcartone.customer.utility.Validations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class CustomerServiceImpl implements CustomerService {
    CustomerRepository repository = new CustomerRepository();

    @Override
    public String saveCustomerData(CustomerEntity customer) {
        if (customer.getCustomerId() != null){
            throw new RuntimeException(Constant.PARTITION_KEY_PROVIDED_ERR_MESSAGE);
        }

        Validations validations = new Validations();
        // Validate mandatory fields
        validations.validateMandatoryField(customer.getFirstName());
        validations.validateMandatoryField(customer.getLastName());
        validations.validateMandatoryField(customer.getEmailAddress());
        validations.validateMandatoryField(customer.getContactNo());
        validations.validateMandatoryField(customer.getMailingAddress());

        // Filter characters not allowed
        validations.validateString(customer.getFirstName());
        validations.validateString(customer.getMiddleName());
        validations.validateString(customer.getLastName());
        validations.validateString(customer.getEmailAddress());
        validations.validateString(customer.getContactNo());
        validations.isValidAddress(customer.getMailingAddress());

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        customer.setCreationDate(dateTime.format(formatter));

        String customerId = UUID.randomUUID().toString();
        customer.setCustomerId(customerId);

        return repository.saveCustomer(customer);
    }

    @Override
    public String getCustomerData(String customerId) {
        Validations validations = new Validations();
        validations.validateString(customerId);

        return repository.getCustomer(customerId);
    }
}
