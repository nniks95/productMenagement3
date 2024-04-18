package com.nikola.spring.service;

import com.nikola.spring.dto.CartDto;
import com.nikola.spring.dto.CartItemDto;

import java.util.List;

public interface CartItemService {

    CartItemDto addCartItem(CartItemDto cartItem, Integer productId);

    List<CartItemDto> listAllItemsByCartId(Integer cartId);

    void deleteItemById(Integer itemId);

    void removeAllByProductId(Integer productId);

    void removeAllByCartId(Integer cartId);

    CartItemDto getCartItemById(Integer cartItemId);




}
