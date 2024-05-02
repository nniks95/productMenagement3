package com.nikola.spring.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "customers")
public class CustomerEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 15, nullable = false)
    private String customerPhone;
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "address_id", nullable = false)
    private ShippingAddressEntity shippingAddress;
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public ShippingAddressEntity getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressEntity shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", customerPhone='" + customerPhone + '\'' +
                ", shippingAddress=" + shippingAddress +
                ", user=" + user +
                ", cart=" + cart +
                '}';
    }
}
