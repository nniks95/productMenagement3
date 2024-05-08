package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.CartItemDto;
import com.nikola.spring.dto.CustomerDto;
import com.nikola.spring.dto.ProductDto;
import com.nikola.spring.entities.CartItemEntity;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.repositories.CartItemRepository;
import com.nikola.spring.repositories.ProductRepository;
import com.nikola.spring.service.CartItemService;
import com.nikola.spring.service.CartService;
import com.nikola.spring.service.CustomerService;
import com.nikola.spring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired private TempConverter tempConverter;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private ProductService productsService;
    @Autowired private CustomerService customerService;
    @Autowired private CartService cartService;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    public CartItemDto addCartItem(CartItemDto cartItemDto) {
        CartItemDto returnValue = new CartItemDto();
        ProductDto currentProduct = productsService.getProductById(cartItemDto.getProduct_id());
        CustomerDto currentCustomer = customerService.getCurrentCustomer();
        Integer quantity = 0;
        List<CartItemDto> myItems = cartService.listAllMyCartItems();
        for(CartItemDto item : myItems){
            if(item.getProduct_id() == currentProduct.getId()){
                quantity += item.getQuantity();
                quantity += cartItemDto.getQuantity();
                returnValue.setCart_id(currentCustomer.getCartId());
                returnValue.setId(item.getId());
                returnValue.setQuantity(quantity);
                returnValue.setPrice(currentProduct.getPrice()*returnValue.getQuantity());
                returnValue.setPrice(Double.valueOf(decimalFormat.format(returnValue.getPrice())));
                returnValue.setProduct_id(currentProduct.getId());
                System.out.println(returnValue.toString());
                CartItemEntity cartItemEntity = tempConverter.dtoToEntity(returnValue);
                CartItemEntity storedValue = cartItemRepository.save(cartItemEntity);
                cartService.refreshCartState(storedValue.getCart().getId());
                returnValue = tempConverter.entityToDto(storedValue);
                return  returnValue;
            }
        }
            returnValue.setCart_id(currentCustomer.getCartId());
            returnValue.setProduct_id(currentProduct.getId());
            returnValue.setQuantity(cartItemDto.getQuantity());
            returnValue.setPrice(currentProduct.getPrice()*cartItemDto.getQuantity());
            returnValue.setPrice(Double.valueOf(decimalFormat.format(returnValue.getPrice())));
            CartItemEntity cartItemEntity = tempConverter.dtoToEntity(returnValue);
            CartItemEntity storedItem = cartItemRepository.save(cartItemEntity);
            cartService.refreshCartState(storedItem.getCart().getId());
            return tempConverter.entityToDto(storedItem);
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
    public List<CartItemDto> listAllItemsByProductId(Integer productId) {
        List<CartItemDto> returnValue = new ArrayList<>();
        List<CartItemEntity> allCartItems = cartItemRepository.findAllByProductId(productId);
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
    public void clearMyCart() {
        cartItemRepository.deleteAllByCartId(customerService.getCurrentCustomer().getCartId());
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
