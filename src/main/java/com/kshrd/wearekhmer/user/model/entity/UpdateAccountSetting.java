package com.kshrd.wearekhmer.user.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAccountSetting {

    @NotNull(message = "username can not be null.")
    @NotBlank(message = "username name can not be blank.")
    private String username;

    @JsonIgnore
    private String email;
    private String dateOfBirth;
    private String gender;

    private List<WorkingExperienceResponse> workingExperiences;
    private List<EducationResponse> educations;
    private List<QuoteResponse>  bio;

}
