package com.kshrd.wearekhmer.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class UtilConfig {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
