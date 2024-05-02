package com.nikola.spring.controler;


import com.nikola.spring.dto.UserDto;
import com.nikola.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserControler {

    @Autowired private UserService userService;

    @GetMapping(value = "/allUsers")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDto>> listAllUsers(){
        return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/userDetails/{userId}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userId")Integer userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    @GetMapping(value = "/currentUser")
    public ResponseEntity<UserDto> getCurrentUser(){
        return new ResponseEntity<>(userService.getCurrentUser(),HttpStatus.OK);
    }

    @PutMapping(value = "/updateUser")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<UserDto> updateUserInfo(@Validated @RequestBody UserDto userDto, Errors errors){
        return new ResponseEntity<>(userService.updateUserInfo(userDto),HttpStatus.OK);
    }



}
