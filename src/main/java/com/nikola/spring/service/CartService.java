package com.nikola.spring.service;

import com.nikola.spring.dto.CartDto;

import java.util.List;

public interface CartService {

    CartDto getCartById(Integer cartId);

    CartDto validateCart();

    CartDto refreshCartState(Integer cartId);

    CartDto refreshAllCarts();

    List<CartDto> listAllCarts();



}
