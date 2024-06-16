package com.nikola.spring.interceptors;

import com.nikola.spring.dto.UserDto;
import com.nikola.spring.exceptions.SuspendedUserException;
import com.nikola.spring.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        UserDto userDto = new UserDto();
        userDto.setEnabled((short)1);
        try{
            Optional<UserDto> userDtoOptional = Optional.ofNullable(userService.getCurrentUser());
            if(userDtoOptional.isPresent()){
                userDto = userDtoOptional.get();
            }
        }catch (Exception e){

        }
        if(userDto.getEnabled() == 0){
            throw new SuspendedUserException(new Error("Account suspended."));
        }
        return true;
    }
}
