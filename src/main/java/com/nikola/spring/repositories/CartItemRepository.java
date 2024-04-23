package com.nikola.spring.repositories;

import com.nikola.spring.entities.CartItemEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity,Integer> {

    List<CartItemEntity> findAllByCartId(Integer cartId);

    void deleteAllByCartId(Integer cartId);
    void deleteAllByProductId(Integer productId);


}
