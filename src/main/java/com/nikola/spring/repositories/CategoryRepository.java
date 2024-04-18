package com.nikola.spring.repositories;

import com.nikola.spring.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {

    Optional <CategoryEntity> findByName(String name);
    //funkcijom findby iz spring date jpa mozemo izvuci kategoriju pomocu tog parametra

}
