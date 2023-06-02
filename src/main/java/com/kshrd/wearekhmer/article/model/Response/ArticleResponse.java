package com.kshrd.wearekhmer.article.model.Response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponse {

    private String title;
    private String subTitle;
    private String description;
    private String image;
    private String categoryId;
    private String photoUrl;
}
