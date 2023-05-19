package com.kshrd.wearekhmer.article.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleUpdateRequest {
    private String articleId;
    private String title;
    private String subTitle;
    private String description;
    private String categoryId;
}
