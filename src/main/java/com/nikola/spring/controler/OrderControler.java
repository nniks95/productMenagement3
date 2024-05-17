package com.nikola.spring.controler;


import com.nikola.spring.dto.OrderDto;
import com.nikola.spring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderControler {

    @Autowired private OrderService orderService;

    @PostMapping(value = "/addOrder")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<String> addOrder(){
        String message = "You have successfuly placed order";
        orderService.addOrder();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping(value = "/allOrders")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity <List<OrderDto>> listAllOrders(){
        return new ResponseEntity<>(orderService.listAllOrders(),HttpStatus.OK);
    }

    @GetMapping(value = "/orderDetails/{orderId}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity <OrderDto> getOrderById(@PathVariable("orderId") Integer orderId){
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteOrder/{orderId}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteOrderById(@PathVariable("orderId") Integer orderId){
        String message = "Order is deleted.";
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @GetMapping(value = "/allOrders/{cartId}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<List<OrderDto>> allOrdersByCartId(@PathVariable("cartId") Integer cartId){
        return new ResponseEntity<>(orderService.listAllByCartId(cartId),HttpStatus.OK);
    }




}
