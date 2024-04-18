package com.nikola.spring.repositories;

import com.nikola.spring.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {

//    @Query(value = "SELECT * FROM product WHERE category_id = :categoryId",nativeQuery = true)
//    List<ProductEntity> findAllByCategoryId(@Param("categoryId") Integer categoryId);

      List<ProductEntity> findAllByCategoryId(Integer categoryId);




}
