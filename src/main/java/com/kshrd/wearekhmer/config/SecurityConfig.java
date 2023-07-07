package com.kshrd.wearekhmer.config;


import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

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

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("https://api.domrra.site");
        return new OpenAPI().servers(List.of(server));
    }

}
