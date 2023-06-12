package com.kshrd.wearekhmer.notification.entity.response;

import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequest;
import com.kshrd.wearekhmer.user.model.entity.Education;
import com.kshrd.wearekhmer.user.model.entity.Quote;
import com.kshrd.wearekhmer.user.model.entity.WorkingExperience;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private List<WorkingExperience> workingExperience;

    private List<Education> education;
    private List<Quote> quote;
    private String reason;

}
