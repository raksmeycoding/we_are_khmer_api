package com.kshrd.wearekhmer.userRating.reponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInformationResponse {
    private String user_id;
    private String username;
    private String email;
    private String gender;
    private String dateOfBirth;

    private String photoUrl;
}
