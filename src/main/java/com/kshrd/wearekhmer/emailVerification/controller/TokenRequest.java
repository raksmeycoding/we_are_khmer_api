package com.kshrd.wearekhmer.emailVerification.controller;


import lombok.Data;

@Data
public class TokenRequest {
    String token;


    public TokenRequest() {
    }

    public TokenRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
