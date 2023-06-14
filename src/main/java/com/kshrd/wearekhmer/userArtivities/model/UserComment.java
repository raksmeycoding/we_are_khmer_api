package com.kshrd.wearekhmer.userArtivities.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor

public class UserComment {
    private String comment_id;
    private String user_id;

    private String article_id;

    private String comment;

    private String createAt;
    private String photo_url;

    private String username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String update;


    private AuthorReplyCommentMapperResponse author_reply;


}
