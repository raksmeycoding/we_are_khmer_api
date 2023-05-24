package com.kshrd.wearekhmer.utils;


import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@NoArgsConstructor
public class WeAreKhmerCurrentUser {

    private String userId;

    private List<String> roles;


    public String getUserId() {
        GenericResponse genericResponse;
        try {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = (UsernamePasswordAuthenticationToken) authentication;

            UserApp userApp = (UserApp) usernamePasswordAuthenticationToken.getPrincipal();

            return userApp.getUserId();

        } catch (Exception exception) {
            if (exception instanceof ClassCastException) {
                throw new CustomRuntimeException("You are not login. Please register first.");
            }
            throw new RuntimeException();
        }
    }


    public List<String> getAuthorities() {
        try {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = (UsernamePasswordAuthenticationToken) authentication;

            UserApp userApp = (UserApp) usernamePasswordAuthenticationToken.getPrincipal();

            System.out.println(userApp.getAuthorities());
            System.out.println(userApp.getRoles());

            List<String> authorities =
                    userApp.getRoles();
            return authorities;

        } catch (Exception exception) {
            if (exception instanceof ClassCastException) {
                throw new CustomRuntimeException("You are not login. Please register first.");
            }
            throw new RuntimeException();
        }
    }


}
