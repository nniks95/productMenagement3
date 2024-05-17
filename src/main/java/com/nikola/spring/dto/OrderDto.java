package com.nikola.spring.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class OrderDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer cartId;
    private Double orderPrice;
    private String createTime;
    private Integer orderAddressId;
    private List<Integer> orderItemsIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderAddressId() {
        return orderAddressId;
    }

    public void setOrderAddressId(Integer orderAddressId) {
        this.orderAddressId = orderAddressId;
    }

    public List<Integer> getOrderItemsIds() {
        return orderItemsIds;
    }

    public void setOrderItemsIds(List<Integer> orderItemsIds) {
        this.orderItemsIds = orderItemsIds;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", cartId=" + cartId +
                ", orderPrice=" + orderPrice +
                ", createTime='" + createTime + '\'' +
                ", orderAddressId=" + orderAddressId +
                ", orderItemsIds=" + orderItemsIds +
                '}';
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }
}
