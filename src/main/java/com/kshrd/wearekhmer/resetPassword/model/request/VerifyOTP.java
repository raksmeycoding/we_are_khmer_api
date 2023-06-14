package com.kshrd.wearekhmer.resetPassword.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyOTP {
    @NotNull(message = "otp must be not null")
    @NotEmpty(message = "otp must be not empty")
    private String otp;
}
