package com.kshrd.wearekhmer.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountSettingResponse {
    private String authorId;
    private String username;
    private String email;
    private String profile;
    private Date date;
    private String gender;
    private List<WorkingExperienceResponse> workingExperience;
    private List<EducationResponse> education;
    private List<Quote> bio;

}
