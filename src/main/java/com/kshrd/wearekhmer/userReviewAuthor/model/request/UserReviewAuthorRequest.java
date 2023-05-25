package com.kshrd.wearekhmer.userReviewAuthor.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserReviewAuthorRequest {
    private String author_id;
    private String comment;
}
