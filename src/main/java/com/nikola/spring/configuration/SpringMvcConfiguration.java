package com.nikola.spring.configuration.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.nikola.spring")
public class SpringMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public ModelMapper getMapper(){
        return new ModelMapper();
    }
}
