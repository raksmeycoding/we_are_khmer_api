package com.kshrd.wearekhmer.opt.model;

import lombok.*;

import java.sql.Timestamp;


@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Otp {
    private String optId;
    private String token;

    private String email;
    private String temp_password;

    private String createAt;
    private Timestamp expiredAt;

    private Boolean isexpired;
    private String userId;
}
