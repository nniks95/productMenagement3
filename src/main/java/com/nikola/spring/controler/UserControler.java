package com.nikola.spring.controler;


import com.nikola.spring.dto.UserDto;
import com.nikola.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
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

    @GetMapping(value = "userDetails/{userEmail}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("userEmail") String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    @PostMapping(value = "/suspendUser/{userId}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<String> suspendUser(@PathVariable("userId") Integer userId){
        userService.suspendUser(userId);
        String message = "User has been suspended";
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @PostMapping(value = "reactivateUser/{userId}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<String> reactivateUser(@PathVariable("userId") Integer userId){
        userService.reactivateUser(userId);
        String message = "User is not suspended anymore";
        return new ResponseEntity<>(message,HttpStatus.OK);
    }



}
