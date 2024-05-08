package com.nikola.spring.repositories;

import com.nikola.spring.entities.OrderAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAddressRepository extends JpaRepository<OrderAddressEntity,Integer> {
}
