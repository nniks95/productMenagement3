package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.CartItemDto;
import com.nikola.spring.entities.CartItemEntity;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.repositories.CartItemRepository;
import com.nikola.spring.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired private TempConverter tempConverter;
    @Autowired private CartItemRepository cartItemRepository;

    @Override
    public CartItemDto addCartItem(CartItemDto cartItem, Integer productId) {
        return null;
    }

    @Override
    public List<CartItemDto> listAllItemsByCartId(Integer cartId) {
        List<CartItemDto> returnValue = new ArrayList<>();
        List<CartItemEntity> allCartItems = cartItemRepository.findAllByCartId(cartId);
        for(CartItemEntity cartItem:allCartItems){
            returnValue.add(tempConverter.entityToDto(cartItem));
        }
        return returnValue;
    }

    @Override
    public void deleteItemById(Integer itemId) {
        CartItemDto cartItemDto = getCartItemById(itemId);
        cartItemRepository.delete(tempConverter.dtoToEntity(cartItemDto));
        cartItemRepository.flush();
    }

    @Override
    public void removeAllByProductId(Integer productId) {
        cartItemRepository.deleteAllByProductId(productId);
        cartItemRepository.flush();
    }

    @Override
    public void removeAllByCartId(Integer cartId) {
        cartItemRepository.deleteAllByCartId(cartId);
        cartItemRepository.flush();

    }

    @Override
    public CartItemDto getCartItemById(Integer cartItemId) {
        CartItemDto returnValue = null;
        Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findById(cartItemId);
        if(cartItemEntityOptional.isPresent()){
            returnValue = tempConverter.entityToDto(cartItemEntityOptional.get());
        }else{
            throw new InstanceUndefinedException(new Error("Cart Item undefined"));
        }
        return returnValue;
    }
}
