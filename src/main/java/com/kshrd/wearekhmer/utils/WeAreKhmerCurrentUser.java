package com.kshrd.wearekhmer.utils;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor
public class WeAreKhmerCurrentUser {

    private String userId;


    public String getUserId() {
        GenericResponse genericResponse;

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = (UsernamePasswordAuthenticationToken) authentication;

        UserApp userApp = (UserApp) usernamePasswordAuthenticationToken.getPrincipal();

        return userApp.getUserId();


    }

}
