package com.kshrd.wearekhmer.requestRequest;


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


