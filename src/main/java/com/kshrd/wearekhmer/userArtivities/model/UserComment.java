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
    private String username;
    private String photo_url;
    private String article_id;
    private String comment;
    private String createAt;
    private String update;

    private String author_reply_image;
    private String author_replay_name;
    private String author_replay_comment;

    //mapper need



}
