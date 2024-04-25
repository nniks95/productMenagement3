package com.nikola.spring.repositories;

import com.nikola.spring.entities.CustomerEntity;
import com.nikola.spring.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Integer> {

    //Optional<CustomerEntity> findByUserName(String username);
}
