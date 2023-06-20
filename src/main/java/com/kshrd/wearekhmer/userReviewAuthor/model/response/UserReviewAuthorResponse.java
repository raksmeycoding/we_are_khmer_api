package com.kshrd.wearekhmer.userReviewAuthor.model.response;

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

public class UserReviewAuthorResponse {
    private String user_review_author_id;
    private String user_id;

    private String senderName;
    private String photoUrl;
    private String author_id;
    private String author_name;
    private Timestamp create_at;
    private Timestamp update_at;
    private String comment;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parent_id;
}
