package com.nikola.spring.repositories;

import com.nikola.spring.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {

    Optional<List<OrderEntity>> findByCartId(Integer cartId);
}
