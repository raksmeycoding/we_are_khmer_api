package com.kshrd.wearekhmer.utils;


import com.kshrd.wearekhmer.config.SecurityConfig;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

@NoArgsConstructor
public class WeAreKhmerCurrentUser {

    private String userId;


    public String getUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = (UsernamePasswordAuthenticationToken) authentication;

        UserApp userApp = (UserApp) usernamePasswordAuthenticationToken.getPrincipal();
        return userApp.getUserId();
    }

}
