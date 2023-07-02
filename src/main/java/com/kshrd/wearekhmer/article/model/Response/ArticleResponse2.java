package com.kshrd.wearekhmer.article.model.Response;


import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    
    private String article_id;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String user_id;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String category_id;

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String sub_title;

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private Timestamp publish_date;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private Timestamp updateAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String image;

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private Integer count_view;

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private boolean isban;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String hero_card_in;



//    author
@JsonInclude(JsonInclude.Include.NON_NULL)

    private String photo_url;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String author_name;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String category_name;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private Integer react_count;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private Boolean bookmarked;
    @JsonInclude(JsonInclude.Include.NON_NULL)

    private Boolean reacted;

}
