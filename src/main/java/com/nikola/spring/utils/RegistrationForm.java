package com.nikola.spring.utils;

import com.nikola.spring.dto.CustomerDto;
import com.nikola.spring.dto.ShippingAddressDto;
import com.nikola.spring.dto.UserDto;
import com.nikola.spring.entities.CustomerEntity;
import com.nikola.spring.entities.UserEntity;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.io.Serial;
import java.io.Serializable;

public class RegistrationForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Valid
    private UserDto user;
    @Valid
    private CustomerDto customer;
    @Valid
    private ShippingAddressDto shippingAddress;


    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public ShippingAddressDto getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressDto shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
