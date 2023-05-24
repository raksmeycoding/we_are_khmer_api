package com.kshrd.wearekhmer.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserTemPassword {
    private String userEmail;
    private String password;
    private String code;
}
