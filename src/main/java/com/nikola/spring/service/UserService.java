package com.nikola.spring.service;

import com.nikola.spring.dto.UserDto;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> listAllUsers();

    UserDto getUserById(Integer userId);

    UserDto getCurrentUser();

    void suspendUser(Integer userId);

    void reactivateUser(Integer userId);

    UserDto updateUserInfo(UserDto user);

    UserDto getUserByEmail(String email);

    Optional<Authentication> authenticateUser(String username, String password);




}
