package com.kshrd.wearekhmer.requestRequest;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NormalUserRequest {


    @NotBlank(message = "email cannot be blank")
    @NotNull(message = "email cannot be null")
    private String email;

    @NotBlank(message = "password cannot be blank")
    @NotNull(message = "password cannot be null")
    private String password;



    @NotBlank(message = "gender cannot be blank")
    @NotNull(message = "gender cannot be null")
    private String gender;

}


