package com.kshrd.wearekhmer.config;


import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    };


    @Bean
    public WeAreKhmerCurrentUser weAreKhmerCurrentUser() {
        return new WeAreKhmerCurrentUser();
    }



}
