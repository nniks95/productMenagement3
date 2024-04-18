package com.nikola.spring.repositories;

import com.nikola.spring.dto.ProductImageDto;
import com.nikola.spring.entities.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity,Integer> {

    Optional<ProductImageEntity> findByProductId(Integer productId);



}
