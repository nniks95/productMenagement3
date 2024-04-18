package com.nikola.spring.service;

import com.nikola.spring.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> listAllUsers();

    UserDto getUserById(Integer userId);

    UserDto getCurrentUser();

    void suspendUser(Integer userId);

    void reactivateUser(Integer userId);

    UserDto updateUserInfo(UserDto user);




}
