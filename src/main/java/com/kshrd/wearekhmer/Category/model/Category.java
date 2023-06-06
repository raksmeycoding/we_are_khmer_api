package com.kshrd.wearekhmer.Category.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class Category {

    @NotNull(message = "Category_id must be not null.")
    @NotBlank(message = "Category_id must be not blank.")
    private String categoryId;

    @NotNull(message = "CategoryName must be not null.")
    @NotBlank(message = "CategoryName must be not blank.")
    private String categoryName;
    private String categoryImage;

}
