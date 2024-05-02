package com.nikola.spring.repositories;

import com.nikola.spring.entities.CartItemEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity,Integer> {

    List<CartItemEntity> findAllByCartId(Integer cartId);
    Optional<CartItemEntity> findByProductId(Integer productId);

    void deleteAllByCartId(Integer cartId);
    void deleteAllByProductId(Integer productId);

    @Query(value = "SELECT SUM(price) FROM cart_items WHERE cart_id = :cartId", nativeQuery = true)
    Optional<Double> calculateCartPrice(@Param("cartId") Integer cartId);



}
