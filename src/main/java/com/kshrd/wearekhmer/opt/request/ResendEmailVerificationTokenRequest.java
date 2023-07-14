package com.kshrd.wearekhmer.opt.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ResendEmailVerificationTokenRequest {


    @NotBlank( message = "Email must be not blank.")
    @NotNull(message = "Email must be not null.")
    private String email;


    @JsonIgnore
    private String password;
}
