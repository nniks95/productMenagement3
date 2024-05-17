package com.nikola.spring.configuration;

import com.nikola.spring.interceptors.UserInterceptor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.nikola.spring")
public class SpringMvcConfiguration implements WebMvcConfigurer {

    @Autowired private UserInterceptor userInterceptor;

    @Bean
    public ModelMapper getMapper(){
        ModelMapper returnValue = new ModelMapper();
        returnValue.getConfiguration().setAmbiguityIgnored(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        returnValue.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //preskace ukoliko se polja ne poklapaju(Timestamp -> String)
        return returnValue;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(userInterceptor);
    }



}
