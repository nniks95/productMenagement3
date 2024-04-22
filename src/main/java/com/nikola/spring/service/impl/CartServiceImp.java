package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.CartDto;
import com.nikola.spring.entities.CartEntity;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.repositories.CartRepository;
import com.nikola.spring.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImp implements CartService {
    @Autowired private TempConverter tempConverter;
    @Autowired private CartRepository cartRepository;


    @Override
    public CartDto getCartById(Integer cartId) {
        CartDto returnValue = null;
        Optional<CartEntity> cartEntityOptional = cartRepository.findById(cartId);
        if(cartEntityOptional.isPresent()){
            returnValue = tempConverter.entityToDto(cartEntityOptional.get());
        }else{
            throw new InstanceUndefinedException(new Error("Cart undefined"));
        }
        return returnValue;
    }

    @Override
    public CartDto validateCart() {
        return null;
    }

    @Override
    public CartDto refreshCartState(Integer cartId) {
        return null;
    }

    @Override
    public CartDto refreshAllCarts() {
        return null;
    }

    @Override
    public List<CartDto> listAllCarts() {
        List<CartDto> returnValue = new ArrayList<>();
        List<CartEntity> allCarts = cartRepository.findAll();
        for(CartEntity cart:allCarts){
            returnValue.add(tempConverter.entityToDto(cart));
        }
        return null;
    }
}
