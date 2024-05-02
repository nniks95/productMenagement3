package com.nikola.spring.service;

import com.nikola.spring.dto.CartDto;
import com.nikola.spring.dto.CartItemDto;

import java.util.List;

public interface CartItemService {

    CartItemDto addCartItem(CartItemDto cartItem);

    List<CartItemDto> listAllItemsByCartId(Integer cartId);

    void deleteItemById(Integer itemId);

    void removeAllByProductId(Integer productId);

    void clearMyCart();

    CartItemDto getCartItemById(Integer cartItemId);




}
