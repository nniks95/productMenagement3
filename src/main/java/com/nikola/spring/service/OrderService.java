package com.nikola.spring.service;

import com.nikola.spring.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto addOrder();

    List<OrderDto> listAllOrders();

    OrderDto getOrderById(Integer orderId);

    void deleteOrder(Integer orderId);

    List<OrderDto> listAllByCartId(Integer cartId);




}
