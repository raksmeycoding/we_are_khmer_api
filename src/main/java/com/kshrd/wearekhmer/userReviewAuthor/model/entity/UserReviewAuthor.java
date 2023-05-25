package com.kshrd.wearekhmer.userReviewAuthor.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
@Builder
public class UserReviewAuthor {
    private String user_review_author_id;
    private String user_id;
    private String author_id;
    private String author_name;
    private Timestamp create_at;
    private Timestamp update_at;
    private String comment;
    private String parent_id;


}
