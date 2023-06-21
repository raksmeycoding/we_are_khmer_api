package com.kshrd.wearekhmer.article.response;


import com.fasterxml.jackson.annotation.JsonInclude;
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
    private Timestamp updateAt;
    private String image;
    private Integer count_view;
    private boolean isban;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String hero_card_in;

    private String photo_url;
    private String author_name;
    private String category_name;
    private Integer react_count;
}
