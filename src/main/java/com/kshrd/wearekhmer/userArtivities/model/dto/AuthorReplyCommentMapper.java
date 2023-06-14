package com.kshrd.wearekhmer.userArtivities.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorReplyCommentMapper {

    private String comment_id;
    private String comment;
}
