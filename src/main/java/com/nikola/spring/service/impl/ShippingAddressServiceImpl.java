package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.ShippingAddressDto;
import com.nikola.spring.dto.UserDto;
import com.nikola.spring.entities.CustomerEntity;
import com.nikola.spring.entities.ShippingAddressEntity;
import com.nikola.spring.entities.UserEntity;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.repositories.CustomerRepository;
import com.nikola.spring.repositories.ShippingAddressRepository;
import com.nikola.spring.repositories.UserRepository;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        ShippingAddressDto returnValue = null;
        Optional<CustomerEntity> customerOptional = customerRepository.findByUserName(currentUsername);
        if(customerOptional.isPresent()){
            if(currentUsername == null){
                throw new RuntimeException("User not found");
            }

            ShippingAddressEntity currentShippingAddress = customerOptional.get().getShippingAddress();
            if(currentShippingAddress == null){
                throw new RuntimeException("Shipping address not found for the customer");
            }

            currentShippingAddress.setAddress(shippingAddress.getAddress());
            currentShippingAddress.setCity(shippingAddress.getCity());
            currentShippingAddress.setCountry(shippingAddress.getCountry());
            currentShippingAddress.setPostCode(shippingAddress.getPostCode());
            currentShippingAddress.setState(shippingAddress.getState());

            returnValue = tempConverter.entityToDto(currentShippingAddress);
        }
        return returnValue;
    }
}
