package com.kshrd.wearekhmer.article.repository;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterArticleCriteria {
    private String title;
    private Date publishDate;

    private Date startDate;
    private Date endDate;
    private String categoryId;

    private String view;

}
