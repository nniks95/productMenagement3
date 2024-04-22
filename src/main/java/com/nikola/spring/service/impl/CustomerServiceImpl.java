package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.CustomerDto;
import com.nikola.spring.entities.CustomerEntity;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.repositories.CustomerRepository;
import com.nikola.spring.service.CustomerService;
import com.nikola.spring.utils.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private TempConverter tempConverter;

    @Autowired private CustomerRepository customerRepository;
    @Override
    public CustomerDto addCustomer(RegistrationForm form) {
        return null;
    }

    @Override
    public List<CustomerDto> listAllCustomers() {
        List<CustomerDto> returnValue = new ArrayList<>();
        List<CustomerEntity> allCustomers = customerRepository.findAll();
        for(CustomerEntity customer:allCustomers){
            returnValue.add(tempConverter.entityToDto(customer));
        }
        return returnValue;
    }

    @Override
    public CustomerDto getCustomerById(Integer customerId) {
        CustomerDto returnValue = null;
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findById(customerId);
        if(customerEntityOptional.isPresent()){
            returnValue = tempConverter.entityToDto(customerEntityOptional.get());
        }else{
            throw new InstanceUndefinedException(new Error("Customer undefined"));
        }
        return returnValue;
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        CustomerDto customer = getCustomerById(customerId);
        customerRepository.deleteById(customer.getId());
        customerRepository.flush();

    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customer) {
        CustomerDto returnValue = getCurrentCustomer();
        returnValue.setShippingAddressId(customer.getShippingAddressId());
        returnValue.setCartId(customer.getCartId());
        returnValue.setCustomerPhone(customer.getCustomerPhone());


        return returnValue;
    }

    @Override
    public CustomerDto getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentCustomer = authentication.getName();
        CustomerDto returnValue = null;

        Optional<CustomerEntity> customerEntityOptional = customerRepository.findByUserName(currentCustomer);
        if(customerEntityOptional.isPresent()){
            if(customerEntityOptional == null){
                throw new RuntimeException("Customer not found");
            }
            returnValue = tempConverter.entityToDto(customerEntityOptional.get());
        }
        return returnValue;
    }
}
