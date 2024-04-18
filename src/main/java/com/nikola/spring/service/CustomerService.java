package com.nikola.spring.service;

import com.nikola.spring.dto.CustomerDto;
import com.nikola.spring.utils.RegistrationForm;

import java.util.List;

public interface CustomerService {

    CustomerDto addCustomer(RegistrationForm form);

    List<CustomerDto> listAllCustomers();

    CustomerDto getCustomerById(Integer customerId);

    void deleteCustomer(Integer customerId);

    CustomerDto updateCustomer(CustomerDto customer);

    CustomerDto getCurrentCustomer();


}
