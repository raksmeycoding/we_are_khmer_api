package com.kshrd.wearekhmer.user.response;


import lombok.Data;

@Data
public class UserCheckResponse {
    private Boolean verified;
    private String message;
}
