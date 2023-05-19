package com.kshrd.wearekhmer.Category.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;


@Builder
@Data
@AllArgsConstructor
public class Category {
    private String categoryId;
    private String categoryName;
    private String categoryImage;

}