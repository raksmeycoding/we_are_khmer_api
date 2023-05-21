package com.kshrd.wearekhmer.bookmark.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookmarkRequest {
    private String userId;
    private String articleId;
}
