package com.kshrd.wearekhmer.user.model.dto;


import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAppDTO {
    private String userId;
    private String userName;

    private String email;

//    private String password;

    private String photoUrl;


    private Timestamp dataOfBirth;

    private boolean isEnable;


    private Boolean isAuthor;

    private String gender;

    private List<String> roles;
}

