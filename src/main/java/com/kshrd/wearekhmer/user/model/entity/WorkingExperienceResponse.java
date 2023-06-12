package com.kshrd.wearekhmer.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WorkingExperienceResponse {
    private String workingExperienceId;
    private String workingExperienceName;
}

