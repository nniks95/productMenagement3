package com.nikola.spring.controler;

import com.nikola.spring.dto.CartDto;
import com.nikola.spring.dto.CartItemDto;
import com.nikola.spring.exceptions.DataNotValidatedException;
import com.nikola.spring.service.CartItemService;
import com.nikola.spring.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/carts")
public class CartControler {

    @Autowired private CartService cartService;
    @Autowired private CartItemService cartItemService;

    @GetMapping(value = "/allCarts")
    public ResponseEntity<List<CartDto>> listAllCarts(){
        return new ResponseEntity<>(cartService.listAllCarts(), HttpStatus.OK);
    }

    @GetMapping(value = "/cartDetails/{cartId}")
    public ResponseEntity<CartDto> getCartById(@PathVariable("cartId") Integer cartId){
        return new ResponseEntity<>(cartService.getCartById(cartId),HttpStatus.OK);
    }

    @PostMapping(value = "/addItem")
    public ResponseEntity<String> addItem(@Validated @RequestBody CartItemDto cartItemDto, Errors errors){
        if(errors.hasErrors()){
            throw new DataNotValidatedException(new Error("Item not validated"));
        }
        String message = "Item successfuly added to cart.";
        cartItemService.addCartItem(cartItemDto);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @GetMapping(value = "/getMyCart")
    public ResponseEntity<List<CartItemDto>> getMyCart(){
        return new ResponseEntity<>(cartService.listAllMyCartItems(),HttpStatus.OK);
    }





}
