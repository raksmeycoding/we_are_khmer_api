package com.kshrd.wearekhmer.article.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@AllArgsConstructor
@Data
@Builder
public class ArticleResponse {
    private String article_id;
    private String user_id;
    private String category_id;
    private String title;
    private String sub_title;
    private Timestamp publish_date;
    private String description;
    private Timestamp updateat;
    private String image;
    private Integer count_view;
    private boolean isban;
    private String hero_card_in;

    private String photo_url;
    private String author_name;
    private String category_name;
    private String react_count;
}
