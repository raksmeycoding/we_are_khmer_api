package com.kshrd.wearekhmer.article.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
public class Article {
    private String articleId;
    private String title;
    private String subTitle;
    private String publishDate;
    private String description;
    private Timestamp updateAt;
    private String image;
    private Integer countView;
    private Boolean isBan;
    private String heroCardIn;
    private String userId;
    private String categoryId;
}
