package com.nikola.spring.repositories;


import com.nikola.spring.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    //Optional<UserEntity> findByName(String name);

    Optional<UserEntity> findByEmail(String email);



}
