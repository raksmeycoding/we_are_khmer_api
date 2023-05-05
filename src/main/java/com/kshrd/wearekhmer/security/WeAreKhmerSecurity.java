package com.kshrd.wearekhmer.security;


import com.kshrd.wearekhmer.user.service.UserAppDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WeAreKhmerSecurity {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserAppDetailsServiceImpl userAppDetailsService;

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    private static String[] ENDPOINTS_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/v1/user/**",
            "/api/v1/auth/register",
            "/api/v1/auth/login",


            "/api/v1/files/**",
            "/images/**"
    };


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userAppDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cofig) throws Exception {
        return cofig.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/", "/user", "/swagger-ui/index.html").permitAll()
                .authorizeHttpRequests()
//                .requestMatchers("/user").hasRole("USER")
                .requestMatchers("/api/v1/auth/register/as-author/user/**").hasRole("USER")
                .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .and()
                .build();

    }
}
