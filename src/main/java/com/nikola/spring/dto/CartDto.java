package com.nikola.spring.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class CartDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotNull
    private Integer customerId;
    private List<Integer> cartItemsIds;
    @NotNull
    @DecimalMin("0.00")
    private Double cartPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<Integer> getCartItemsIds() {
        return cartItemsIds;
    }

    public void setCartItemsIds(List<Integer> cartItemsIds) {
        this.cartItemsIds = cartItemsIds;
    }

    public Double getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(Double cartPrice) {
        this.cartPrice = cartPrice;
    }
}
