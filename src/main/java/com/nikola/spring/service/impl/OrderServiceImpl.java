package com.nikola.spring.service.impl;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.*;
import com.nikola.spring.entities.OrderAddressEntity;
import com.nikola.spring.entities.OrderEntity;
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
import java.util.List;

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

    private ZoneId  zoneId = ZoneId.of("UTC");


    @Override
    public OrderDto addOrder() {
        OrderDto returnValue = new OrderDto();
        CustomerDto currentCustomer = customerService.getCurrentCustomer();
        CartDto currentCart = cartService.getCartById(currentCustomer.getCartId());
        cartService.validateCart();
        returnValue.setCartId(currentCart.getId());
        returnValue.setPrice(currentCart.getCartPrice());

        ShippingAddressDto shippingAddressDto = shippingAddressService.getAddressById(currentCustomer.getShippingAddressId());
        OrderAddressDto orderAddressDto = tempConverter.shippingAddressToOrderAddress(shippingAddressDto);
        OrderAddressEntity orderAddressEntity = tempConverter.dtoToEntity(orderAddressDto);
        OrderAddressEntity storedAddress = orderAddressRepository.save(orderAddressEntity);
        returnValue.setOrderAddressId(storedAddress.getId());

        OrderEntity orderEntity = tempConverter.dtoToEntity(returnValue);
        //Timestamp currentTimestamp = Timestamp.from(Instant.now()); //Timestamp.valueOf(LocaldateTime.now())
        //orderEntity.setCreateTime(currentTimestamp);
        Instant instant = Instant.now();
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        Timestamp currentTimestamp= Timestamp.valueOf(localDateTime);
        orderEntity.setCreateTime(currentTimestamp);


        return null;
    }

    @Override
    public List<OrderDto> listAllOrders() {
        return null;
    }

    @Override
    public OrderDto getOrderById(Integer orderId) {
        return null;
    }

    @Override
    public void deleteOrder(Integer orderId) {

    }

    @Override
    public List<OrderDto> listAllByCartId(Integer cartId) {
        return null;
    }
}
