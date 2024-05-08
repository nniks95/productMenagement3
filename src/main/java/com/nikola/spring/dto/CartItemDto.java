package com.nikola.spring.dto;

import com.nikola.spring.entities.CartEntity;
import com.nikola.spring.entities.ProductEntity;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

public class CartItemDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotNull
    private Integer product_id;
    @NotNull
    private Integer quantity;
    private Double price;
    private Integer cart_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCart_id() {
        return cart_id;
    }

    public void setCart_id(Integer cart_id) {
        this.cart_id = cart_id;
    }

    @Override
    public String toString() {
        return "CartItemDto{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", cart_id=" + cart_id +
                '}';
    }
}
