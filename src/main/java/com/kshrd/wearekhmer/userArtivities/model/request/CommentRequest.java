package com.kshrd.wearekhmer.userArtivities.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentRequest {
    private String articleId;
    private String comment;
}
