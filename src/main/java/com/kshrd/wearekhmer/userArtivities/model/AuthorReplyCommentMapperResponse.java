package com.kshrd.wearekhmer.userArtivities.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Timer;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AuthorReplyCommentMapperResponse {
    private String  comment_id;
    private String user_id;
    private String article_id;
    private String parent_id;
    private String comment;
    private Timestamp createAt;
    private String username;
    private String photo_url;



}
