package com.nikola.spring.controler;

import com.nikola.spring.dto.CartDto;
import com.nikola.spring.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/carts")
public class CartControler {

    @Autowired private CartService cartService;

    @GetMapping(value = "/allCarts")
    public ResponseEntity<List<CartDto>> listAllCarts(){
        return new ResponseEntity<>(cartService.listAllCarts(), HttpStatus.OK);
    }

    @GetMapping(value = "/cartDetails/{cartId}")
    public ResponseEntity<CartDto> getCartById(@PathVariable("cartId") Integer cartId){
        return new ResponseEntity<>(cartService.getCartById(cartId),HttpStatus.OK);
    }





}
