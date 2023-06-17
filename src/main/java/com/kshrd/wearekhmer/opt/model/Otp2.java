package com.kshrd.wearekhmer.opt.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Otp2 {
    private String token_id;
    private String token;
    private String email;
    private String temp_password;
    private Timestamp createat;
    private Timestamp expiredat;
    private boolean isexpired;
    private String user_id;
}
