package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.CustomerDto;
import com.nikola.spring.dto.ShippingAddressDto;
import com.nikola.spring.dto.UserDto;
import com.nikola.spring.entities.CustomerEntity;
import com.nikola.spring.entities.ShippingAddressEntity;
import com.nikola.spring.entities.UserEntity;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.repositories.CustomerRepository;
import com.nikola.spring.repositories.ShippingAddressRepository;
import com.nikola.spring.repositories.UserRepository;
import com.nikola.spring.service.CustomerService;
import com.nikola.spring.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ShippingAddressServiceImpl implements ShippingAddressService {

    @Autowired private TempConverter tempConverter;
    @Autowired private ShippingAddressRepository shippingAddressRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private CustomerService customerService;



    @Override
    public ShippingAddressDto getAddressById(Integer shippingAddressId) {
        ShippingAddressDto returnValue = null;
        Optional<ShippingAddressEntity> shippingAddressEntityOptional = shippingAddressRepository.findById(shippingAddressId);
        if(shippingAddressEntityOptional.isPresent()){
            returnValue = tempConverter.entityToDto(shippingAddressEntityOptional.get());
        }else{
            throw new InstanceUndefinedException(new Error("Shipping address undefined"));
        }
        return returnValue;
    }

    @Override
    public ShippingAddressDto updateAddress(ShippingAddressDto shippingAddress) {
        CustomerDto currentCustomer = customerService.getCurrentCustomer();
        ShippingAddressDto currentAddress = getAddressById(currentCustomer.getShippingAddressId());
        System.out.println(currentAddress.toString());
        shippingAddress.setId(currentAddress.getId());
        shippingAddress.setCustomerId(currentAddress.getCustomerId());
        ShippingAddressEntity updateAddress = tempConverter.dtoToEntity(shippingAddress);
        updateAddress = shippingAddressRepository.saveAndFlush(updateAddress);
        return tempConverter.entityToDto(updateAddress);
    }


}
