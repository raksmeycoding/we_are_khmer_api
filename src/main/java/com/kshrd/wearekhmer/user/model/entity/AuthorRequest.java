package com.kshrd.wearekhmer.user.model.entity;


import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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


    @NotNull(message = "Author name can not be null.")
    @NotBlank(message = "Author name can not be blank.")
    private String authorName;

    @NotNull(message = "date of birth name can not be null.")
    @NotBlank(message = "Author name can not be blank.")
    private String dateOfBirth;

    @NotNull
    @NotBlank
    private String gender;

    @NotNull
    @NotBlank
    private List<String> workingExperience;


    @NotNull
    @NotBlank
    private List<String> education;

    @NotNull
    @NotBlank
    private List<String> quote;


    @NotNull
    @NotBlank
    private String reason;
}
