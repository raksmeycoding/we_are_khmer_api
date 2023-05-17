package com.kshrd.wearekhmer.Category.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryRequestDTO {
    private String categoryName;
}
