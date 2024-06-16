package com.nikola.spring.controler;

import com.nikola.spring.converter.TempConverter;
import com.nikola.spring.dto.UserDto;
import com.nikola.spring.entities.UserEntity;
import com.nikola.spring.exceptions.DataNotValidatedException;
import com.nikola.spring.service.CustomerService;
import com.nikola.spring.service.UserService;
import com.nikola.spring.utils.AuthenticationRequest;
import com.nikola.spring.utils.JwtUtil;
import com.nikola.spring.utils.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.Optional;

@RestController
public class MainControler {

    @Autowired private UserService userService;
    @Autowired private TempConverter tempConverter;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private CustomerService customerService;

    @PostMapping(value = "/login")
    public ResponseEntity <UserDto> userLogin(@RequestBody AuthenticationRequest authenticationRequest) throws CredentialNotFoundException {
        Optional<Authentication> authOptional =  userService.authenticateUser(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if(authOptional.isEmpty()){
            throw new CredentialNotFoundException("Login failed");
        }
        UserDto user = userService.getUserByEmail(authenticationRequest.getUsername());
        UserEntity userEntity = tempConverter.dtoToEntity(user);
        String jwt = jwtUtil.generateToken(userEntity);
        user = tempConverter.entityToDto(userEntity);
        user.setAuthToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping(value = "/register")
    public ResponseEntity<String> registration(@RequestBody @Validated RegistrationForm registrationForm, Errors errors){
        if(errors.hasErrors()){
            throw new DataNotValidatedException(new Error("Registration form is not valid"));
        }
        customerService.addCustomer(registrationForm);
        String message = "Registration passed.";
        return new ResponseEntity<>(message,HttpStatus.OK);
    }




}
