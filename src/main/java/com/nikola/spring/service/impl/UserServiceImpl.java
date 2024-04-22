package com.nikola.spring.service.impl;


import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.UserDto;
import com.nikola.spring.entities.UserEntity;
import com.nikola.spring.exceptions.InstanceUndefinedException;
import com.nikola.spring.repositories.UserRepository;
import com.nikola.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired private TempConverter tempConverter;

    @Autowired private UserRepository userRepository;


    @Override
    public List<UserDto> listAllUsers() {

        List<UserDto> returnValue = new ArrayList<>();
        List<UserEntity> allUsers = userRepository.findAll();
        for(UserEntity user:allUsers){
            returnValue.add(tempConverter.entityToDto(user));
        }
        return returnValue;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        UserDto returnValue = null;

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        if(userEntityOptional.isPresent()){
            returnValue = tempConverter.entityToDto(userEntityOptional.get());
        }else{
            throw new InstanceUndefinedException(new Error("User undefined"));
        }
        return returnValue;
    }

    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        UserDto returnValue = null;

        Optional<UserEntity> userEntityOptional = userRepository.findUserByName(currentUser);
        if(userEntityOptional.isPresent()){
            if(userEntityOptional == null){
                throw new RuntimeException("User not found");
            }

            UserEntity currentUserEntity = userEntityOptional.get();
            returnValue = tempConverter.entityToDto(currentUserEntity);
        }
        return returnValue;
    }

    @Override
    public void suspendUser(Integer userId) {

    }

    @Override
    public void reactivateUser(Integer userId) {

    }

    @Override
    public UserDto updateUserInfo(UserDto user) {
        UserDto returnValue = getCurrentUser();

        returnValue.setEmail(user.getEmail());
        returnValue.setPassword(user.getPassword());
        return returnValue;
    }
}
