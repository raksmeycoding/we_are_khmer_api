package com.kshrd.wearekhmer.article.model.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BanAuthor {
    private String authorId;
    private String email;
    private String authorName;
    private String authorProfile;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp dateOfBirth;
}
