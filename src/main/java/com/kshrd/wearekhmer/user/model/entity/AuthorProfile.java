package com.kshrd.wearekhmer.user.model.entity;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;


@AllArgsConstructor
@Data
public class AuthorProfile {
    private String userId;
    private String workingExperience;

    private String education;

    private String quote;

}
