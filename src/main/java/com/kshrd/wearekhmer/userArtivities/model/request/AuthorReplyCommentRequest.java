package com.kshrd.wearekhmer.userArtivities.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorReplyCommentRequest {
    private String comment_id;
    private String comment;
}
