package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.CartDto;
import com.nikola.spring.dto.CartItemDto;
import com.nikola.spring.dto.CustomerDto;
import com.nikola.spring.entities.CartEntity;
import com.nikola.spring.entities.CartItemEntity;
import com.nikola.spring.exceptions.DataNotValidatedException;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.exceptions.InvalidCartException;
import com.nikola.spring.repositories.CartItemRepository;
import com.nikola.spring.repositories.CartRepository;
import com.nikola.spring.service.CartService;
import com.nikola.spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired private TempConverter tempConverter;
    @Autowired private CartRepository cartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private CustomerService customerService;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");


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
        CartDto returnValue = getCartById(customerService.getCurrentCustomer().getCartId());
        if(returnValue.getCartItemsIds().isEmpty()){
            throw new InvalidCartException(new Error("Cart is empty."));
        }
        return returnValue;
    }

    @Override
    public void refreshCartState(Integer cartId) {
        CartDto cart = getCartById(cartId);
        Double price = cartItemRepository.calculateCartPrice(cartId).orElse(0d);
        price = Double.valueOf(decimalFormat.format(price));
        cart.setCartPrice(price);
        CartEntity cartEntity = tempConverter.dtoToEntity(cart);
        cartRepository.saveAndFlush(cartEntity);
    }

    @Override
    public void refreshAllCarts() {
        List<CartDto> allCart = listAllCarts();
        for(CartDto cart:allCart){
            refreshCartState(cart.getId());
        }
    }

    @Override
    public List<CartDto> listAllCarts() {
        List<CartDto> returnValue = new ArrayList<>();
        List<CartEntity> allCarts = cartRepository.findAll();
        for(CartEntity cart:allCarts){
            returnValue.add(tempConverter.entityToDto(cart));
        }
        return returnValue;
    }

    @Override
    public List<CartItemDto> listAllMyCartItems() {
        CustomerDto currentCustomer = customerService.getCurrentCustomer();
        List<CartItemEntity> allCartItems = cartItemRepository.findAllByCartId(currentCustomer.getCartId());
        List<CartItemDto> returnValue = new ArrayList<>();
        for(CartItemEntity cartItem:allCartItems){
            returnValue.add(tempConverter.entityToDto(cartItem));
        }
        return returnValue;
    }
}
