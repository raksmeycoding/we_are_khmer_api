package com.kshrd.wearekhmer.security;

import com.kshrd.wearekhmer.user.service.userService.UserAppDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Component
@AllArgsConstructor
public class WeAreKhmerSecurity {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserAppDetailsServiceImpl userAppDetailsService;

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    private static String[] ENDPOINTS_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/api/v1/user/**",
             "/api/v1/auth/register",
            "/api/v1/auth/register/**",
            "/api/v1/auth/login",
            "/images/**",
            "/api/v1/files/file/**",
            "/api/v1/email/**",
            "/api/v1/auth/verification/token",
            "/api/v1/category",
            "/api/v1/article",
            "/api/v1/article/{articleId}",
            "/api/v1/article/{articleId}/status",
            "/api/v1/review/**",
            "/api/v1/auth/register",
            "/api/v1/category/{categoryId}",
            "/api/v1/rating/{authorId}",
            "/api/v1/files/file/filename",
            "/api/v1/comment/article/{articleId}",
            "/api/v1/article/category/**",
            "/api/v1/article/increase/{articleId}",
            //            "/api/v1/notification"
            "/api/v1/rating/rating-bar/{authorId}",
            "/api/v1/order-navbar",
            "/api/v1/author/{authorId}",
            "/api/v1/author/personal-info/{authorId}",
            "/api/v1/reset/**",
            "/api/v1/token/resendEmailVerificationToken",
            "/api/v1/heroCard",
            "/api/v1/heroCard/{type}",
            "/api/v1/article/",
            "/api/v1/author/personal-info",
            "/api/v1/files/**"

    };

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*")); // Allows all origin
        config.setAllowedHeaders(
                Arrays.asList(
                        "X-Requested-With",
                        "Origin",
                        "Content-Type",
                        "Accept",
                        "Authorization",
                        "Access-Control-Allow-Credentials",
                        "Access-Control-Allow-Headers",
                        "Access-Control-Allow-Methods",
                        "Access-Control-Allow-Origin",
                        "Access-Control-Expose-Headers",
                        "Access-Control-Max-Age",
                        "Access-Control-Request-Headers",
                        "Access-Control-Request-Method",
                        "Age",
                        "Allow",
                        "Alternates",
                        "Content-Range",
                        "Content-Disposition",
                        "Content-Description"
                )
        );
        config.setAllowedMethods(
                Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
        );
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userAppDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration cofig
    )
            throws Exception {
        return cofig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {
        return httpSecurity
                .csrf()
                .disable()
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //                .authorizeHttpRequests()
                //                .requestMatchers("/", "/user", "/swagger-ui/index.html").permitAll()
                .authorizeHttpRequests()
//                ⚠️ Smey
//                feature user report author
                .requestMatchers(HttpMethod.DELETE, "/api/v1/report/author").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/report/author").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/report/author/{reportId}").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/report/author/admin/approveOrReject/{authorId}").hasRole("ADMIN")
//                end feature report author
//                ⚠️ Smey reply comment
                .requestMatchers(HttpMethod.POST," /api/v1/comment/article/reply").hasAnyRole("AUTHOR")
//                ebd feature reply comment
//                .requestMatchers("/api/v1/author/{authorId}").hasAnyRole("AUTHOR", "ADMIN")
//                ⚠️Smey
                .requestMatchers(HttpMethod.POST, "/api/v1/article/adminBanArticle/{articleId}").hasRole("ADMIN")
//
//                .requestMatchers(HttpMethod.POST, "/api/v1/files/**")
//                .hasAnyRole("ADMIN", "AUTHOR", "USER")
                .requestMatchers("/api/v1/article/react/**")
                .hasAnyRole("ADMIN", "AUTHOR", "USER")
                .requestMatchers("/api/v1/comment/article")
                .hasAnyRole("ADMIN", "AUTHOR", "USER")
                //                .requestMatchers(HttpMethod.GET,"/api/v1/notification").hasAnyRole("ADMIN","AUTHOR")
                .requestMatchers(HttpMethod.POST,"/api/v1/rating")
                .hasAnyRole("ADMIN", "AUTHOR", "USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/report")
                .hasAnyRole("AUTHOR", "USER")
                .requestMatchers(HttpMethod.GET, "/api/v1/report")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/report")
                .hasRole("ADMIN")
                .requestMatchers("/api/v1/history/**")
                .hasAnyRole("ADMIN", "AUTHOR", "USER")
                .requestMatchers("/api/v1/bookmark/**")
                .hasAnyRole("ADMIN", "AUTHOR", "USER")
                .requestMatchers("/api/v1/author/accept/{userId}")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/api/v1/author/rejected/{userId}").hasRole("ADMIN")
                .requestMatchers("/api/v1/author/authorRequest")
                .hasRole("ADMIN")
                .requestMatchers("/api/v1/auth/register/as-author")
                .hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/category")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/category")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/category")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/article/author")
                .hasRole("AUTHOR")
                .requestMatchers(HttpMethod.PUT, "/api/v1/article/author")
                .hasAnyRole("AUTHOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/article/author")
                .hasRole("AUTHOR")
                .requestMatchers(HttpMethod.GET, "/api/v1/article/author/**")
                .hasRole("AUTHOR")
                .requestMatchers( "/api/v1/notification/admin/**")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/order-navbar")
                .hasRole("ADMIN")
                .requestMatchers("/api/v1/article/admin/**")
                .hasRole("ADMIN")
                .requestMatchers("/api/v1/author/profile").hasRole("AUTHOR")
                .requestMatchers( HttpMethod.POST,"/api/v1/heroCard").hasRole("ADMIN")
                .requestMatchers( HttpMethod.PUT,"/api/v1/heroCard").hasRole("ADMIN")
                .requestMatchers( HttpMethod.DELETE,"/api/v1/heroCard").hasRole("ADMIN")
                .requestMatchers("/api/v1/notification/author/**").hasRole("AUTHOR")
                .requestMatchers(HttpMethod.GET, "/api/v1/author/account-setting").hasRole("AUTHOR")
                .requestMatchers(HttpMethod.PUT, "/api/v1/author/update-account-setting").hasRole("AUTHOR")
                .requestMatchers(HttpMethod.PUT,"/api/v1/author/update-user-image").hasAnyRole("USER","AUTHOR","ADMIN")
                .requestMatchers("/api/v1/author/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/author/authorUser").hasRole("ADMIN")
                .requestMatchers("/api/v1/user//admin/getAllUser/{isAuthor}").hasRole("ADMIN")
                .requestMatchers("/api/v1/notification/readNotification").hasAnyRole("ADMIN","AUTHOR")
                .requestMatchers("/api/v1/notification/readAllNotifications").hasAnyRole("ADMIN","AUTHOR")
                .requestMatchers("api/v1/user/updateName").hasAnyRole("ADMIN", "USER", "AUTHOR")
                .requestMatchers(ENDPOINTS_WHITELIST)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(
                        jwtAuthenticationTokenFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .logout()
                .and()
                .build();
    }
}
