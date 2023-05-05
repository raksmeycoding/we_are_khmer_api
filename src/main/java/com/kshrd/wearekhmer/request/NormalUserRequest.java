package com.kshrd.wearekhmer.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NormalUserRequest {

    private String email;

    private String password;



    private String gender;

}


