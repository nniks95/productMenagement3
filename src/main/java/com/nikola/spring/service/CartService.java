package com.nikola.spring.service;

import com.nikola.spring.dto.CartDto;
import com.nikola.spring.dto.CartItemDto;

import java.util.List;

public interface CartService {

    CartDto getCartById(Integer cartId);

    CartDto validateCart();

    void refreshCartState(Integer cartId);

    void refreshAllCarts();

    List<CartDto> listAllCarts();

    List<CartItemDto> listAllMyCartItems();




}
