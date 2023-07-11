package com.kshrd.wearekhmer.config;


import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
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
        List<Server> servers = new ArrayList<>();

        Server server1 = new Server();
        server1.setUrl("https://api.domrra.site");
        servers.add(server1);

        Server server2 = new Server();
        server2.setUrl("http://localhost:8080");
        servers.add(server2);

        // Add more servers as needed

        return new OpenAPI().servers(servers);
    }

}
