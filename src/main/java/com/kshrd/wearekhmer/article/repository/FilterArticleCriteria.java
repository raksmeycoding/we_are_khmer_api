package com.kshrd.wearekhmer.article.repository;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterArticleCriteria {
    private String title;
    private String publishDate;

    private String startDate;
    private String endDate;
    private String categoryId;

    private String view;
    private String day;

    private String userId;

    private Integer page;

}
