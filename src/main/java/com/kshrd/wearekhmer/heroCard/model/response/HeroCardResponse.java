package com.kshrd.wearekhmer.heroCard.model.response;

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
public class HeroCardResponse {
    private String heroCardId;
    private String categoryId;

    private ArticleResponse2 articleResponse;

    private Integer index;
    private String type;
}
