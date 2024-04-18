package com.nikola.spring.service;

import com.nikola.spring.dto.ShippingAddressDto;

public interface ShippingAddressService {

    ShippingAddressDto getAddressById(Integer shippingAddressId);

    ShippingAddressDto updateAddress(ShippingAddressDto shippingAddress);
}
