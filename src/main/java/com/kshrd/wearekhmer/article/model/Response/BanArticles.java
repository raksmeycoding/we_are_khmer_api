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
public class BanArticles {
    private String articleId;
    private String authorName;
    private String title;
    private String subTitle;

    private Timestamp updateAt;
    private Timestamp publishDate;
    private String articleImage;
    private String authorProfile;

}
