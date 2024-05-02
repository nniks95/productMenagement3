package com.nikola.spring.controler;

import com.nikola.spring.dto.CartItemDto;
import com.nikola.spring.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/cartItems")
public class CartItemControler {

    @Autowired private CartItemService cartItemService;

    @GetMapping(value = "/allCartItems/{cartId}")
    public ResponseEntity<List<CartItemDto>> allCartItemsById(@PathVariable("cartId")Integer cartId){
        return new ResponseEntity<>(cartItemService.listAllItemsByCartId(cartId), HttpStatus.OK);
    }


    @DeleteMapping(value = "/deleteCartItem/{itemId}")
    public ResponseEntity<String> deleteItemById(@PathVariable("itemId") Integer itemId){
        cartItemService.deleteItemById(itemId);
        String message = "Item with id: " +itemId+ " has been deleted.";
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @GetMapping(value = "/cartItemDetails/{cartItemId}")
    public ResponseEntity<CartItemDto> getCartItemById(@PathVariable("cartItemId") Integer cartItemId){
        return new ResponseEntity<>(cartItemService.getCartItemById(cartItemId),HttpStatus.OK);
    }

    @DeleteMapping(value = "/clearMyCart")
    public ResponseEntity<String> clearMyCart(){
        cartItemService.clearMyCart();
        String message = "Cart is cleared.";
        return new ResponseEntity<>(message,HttpStatus.OK);
    }






}
