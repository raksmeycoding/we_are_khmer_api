package com.kshrd.wearekhmer.resetPassword.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reset {
    private String resetPasswordId;
    private String token;
    private String email;
    private boolean isVerified;
}
