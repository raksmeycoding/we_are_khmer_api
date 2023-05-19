package com.kshrd.wearekhmer.article.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ArticleDeleteRequest {
    private String articleId;

}
