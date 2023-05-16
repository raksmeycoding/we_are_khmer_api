package com.kshrd.wearekhmer.user.model.entity;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequest {

    private String authorName;

    private Timestamp dateOfBirth;

    private String gender;
    private List<String> workingExperience;

    private List<String> education;

    private List<String> quote;

    private String reason;
}
