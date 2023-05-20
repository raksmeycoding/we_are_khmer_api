package com.kshrd.wearekhmer.bookmark.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bookmark {
    private String bookmarkId;
    private String userId;
    private String articleId;
    private Timestamp createdAt;
}
