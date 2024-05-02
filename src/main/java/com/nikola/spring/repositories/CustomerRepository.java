package com.nikola.spring.repositories;

import com.nikola.spring.entities.CustomerEntity;
import com.nikola.spring.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Integer> {


    @Query(value = "SELECT c.* FROM customers c INNER JOIN users u ON c.user_id = u.id WHERE u.email = :email", nativeQuery = true)
    Optional<CustomerEntity> findByEmail(@Param("email") String email);

}
