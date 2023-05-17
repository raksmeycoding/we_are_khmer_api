package com.kshrd.wearekhmer.user.model.dto;

import com.kshrd.wearekhmer.user.model.entity.Education;
import com.kshrd.wearekhmer.user.model.entity.Quote;
import com.kshrd.wearekhmer.user.model.entity.WorkingExperience;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthorDTO {

    private String authorId;
    private String authorName;
    private String photoUrl;
    private String dateOfBirth;

    private String gender;

    private List<WorkingExperience> workingExperience;

    private List<Education> education;
    private List<Quote> quote;

}
