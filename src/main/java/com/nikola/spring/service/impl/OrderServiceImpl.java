package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.*;
import com.nikola.spring.entities.CartItemEntity;
import com.nikola.spring.entities.OrderAddressEntity;
import com.nikola.spring.entities.OrderEntity;
import com.nikola.spring.entities.OrderItemEntity;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.repositories.*;
import com.nikola.spring.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderAddressRepository orderAddressRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private TempConverter tempConverter;
    @Autowired private CustomerService customerService;
    @Autowired private CartService cartService;
    @Autowired private CartItemService cartItemService;
    @Autowired private ShippingAddressService shippingAddressService;
    @Autowired private CartItemRepository cartItemRepository;

    private ZoneId  zoneId = ZoneId.of("Europe/Belgrade");

    @Override
    public OrderDto addOrder() {
        OrderDto returnValue = new OrderDto();
        CustomerDto currentCustomer = customerService.getCurrentCustomer();
        CartDto currentCart = cartService.getCartById(currentCustomer.getCartId());
        cartService.validateCart();
        returnValue.setCartId(currentCart.getId());
        returnValue.setOrderPrice(currentCart.getCartPrice());

        ShippingAddressDto shippingAddressDto = shippingAddressService.getAddressById(currentCustomer.getShippingAddressId());
        OrderAddressDto orderAddressDto = tempConverter.shippingAddressToOrderAddress(shippingAddressDto);
        OrderAddressEntity orderAddressEntity = tempConverter.dtoToEntity(orderAddressDto);
        OrderAddressEntity storedAddress = orderAddressRepository.save(orderAddressEntity);
        returnValue.setOrderAddressId(storedAddress.getId());

        OrderEntity orderEntity = tempConverter.dtoToEntity(returnValue);
        Instant instant = Instant.now();
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        Timestamp currentTimestamp= Timestamp.valueOf(localDateTime);
        orderEntity.setCreateTime(currentTimestamp);
        OrderEntity storedOrder = orderRepository.save(orderEntity);


        List<OrderItemDto> orderItemsDto = new ArrayList<>();
        List<CartItemDto> cartItemsDto = cartItemService.listAllItemsByCartId(returnValue.getCartId());
        for(CartItemDto cartItem: cartItemsDto){
            OrderItemDto orderItem = tempConverter.cartItemToOrderItem(cartItem);
            orderItem.setOrderId(storedOrder.getId());
            orderItemsDto.add(orderItem);
        }
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        for(OrderItemDto orderItem: orderItemsDto){
            OrderItemEntity orderItemEntity = tempConverter.dtoToEntity(orderItem);
            OrderItemEntity storedOrderItem = orderItemRepository.save(orderItemEntity);
            orderItemEntities.add(storedOrderItem);
        }
        storedOrder.setOrderItems(orderItemEntities);
        orderRepository.saveAndFlush(storedOrder);
        returnValue = tempConverter.entityToDto(storedOrder);
        cartItemService.clearMyCart();
        return returnValue;
    }

    @Override
    public List<OrderDto> listAllOrders() {
        List<OrderDto> returnValue = new ArrayList<>();
        List<OrderEntity> allOrders = orderRepository.findAll();
        for(OrderEntity order:allOrders){
            returnValue.add(tempConverter.entityToDto(order));
        }
        return returnValue;
    }

    @Override
    public OrderDto getOrderById(Integer orderId) {
        OrderDto returnValue = null;
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(orderId);
        if(orderEntityOptional.isPresent()){
            returnValue = tempConverter.entityToDto(orderEntityOptional.get());
        }else{
            throw new InstanceUndefinedException(new Error("Order not found"));
        }
        return returnValue;
    }

    @Override
    public void deleteOrder(Integer orderId) {
        OrderDto order = getOrderById(orderId);
        orderRepository.deleteById(order.getId());
        orderRepository.flush();
    }

    @Override
    public List<OrderDto> listAllByCartId(Integer cartId) {
        List<OrderDto> returnValue = new ArrayList<>();
        Optional<List<OrderEntity>> orderEntities = orderRepository.findByCartId(cartId);
        if(orderEntities.isPresent()){
            for(OrderEntity order:orderEntities.get()){
                returnValue.add(tempConverter.entityToDto(order));
            }
        }
        return returnValue;
    }
}
