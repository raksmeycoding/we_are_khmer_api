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
public class ArticleResponse2 {

    private String article_id;
    private String title;
    private String sub_title;

    private Timestamp publish_date;

}
