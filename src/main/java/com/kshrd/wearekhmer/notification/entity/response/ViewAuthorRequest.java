package com.kshrd.wearekhmer.notification.entity.response;

import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewAuthorRequest {

    private String authorId;
    private String authorName;
    private String photoUrl;
    private String email;
    private String reason;

    private String isAccepted;

    private Date requestOn;

    private List<WorkingExperienceResponse> workingExperience;

    private List<EducationResponse> education;
    private List<QuoteResponse> quote;


}
