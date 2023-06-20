package com.kshrd.wearekhmer.user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class UpdateAccountSetting {
    private String username;

    @JsonIgnore
    private String email;
    private Date dateOfBirth;
    private String gender;

    private List<WorkingExperienceResponse> workingExperiences;
    private List<EducationResponse> educations;
    private List<QuoteResponse>  bio;

}
