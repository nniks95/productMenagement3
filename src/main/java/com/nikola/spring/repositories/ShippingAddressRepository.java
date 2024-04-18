package com.nikola.spring.repositories;

import com.nikola.spring.entities.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddressEntity,Integer> {
}
