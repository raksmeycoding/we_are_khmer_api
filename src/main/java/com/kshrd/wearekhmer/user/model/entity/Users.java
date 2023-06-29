package com.kshrd.wearekhmer.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Users {
    private String userId;
    private String userName;
    private String email;
    private String photoUrl;
    private String gender;
    private Timestamp dateOfBirth;
}
