package com.kshrd.wearekhmer.bookmark.model.reponse;

import com.kshrd.wearekhmer.article.model.Response.ArticleResponse2;
import com.kshrd.wearekhmer.article.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookmarkResponse {
    private String bookmarkId;
    private String userId;
    private ArticleResponse article;

    private Timestamp createdAt;
}
