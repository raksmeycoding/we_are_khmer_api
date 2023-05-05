package com.kshrd.wearekhmer.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserRepsonse {
    private String email;
    private List<String> roles;
    private String token;
}
