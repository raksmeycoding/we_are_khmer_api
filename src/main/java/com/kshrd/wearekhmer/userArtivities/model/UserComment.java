package com.kshrd.wearekhmer.userArtivities.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String update;
    private String author_replay_name;
    private String author_replay_comment;


}
