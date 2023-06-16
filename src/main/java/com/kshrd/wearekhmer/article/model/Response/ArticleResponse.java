package com.kshrd.wearekhmer.article.model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
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



    //    author
    private String photo_url;
    private String author_name;
    private String category_name;
    private Integer react_count;
}
