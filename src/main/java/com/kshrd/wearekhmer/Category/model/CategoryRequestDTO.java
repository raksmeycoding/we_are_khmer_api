package com.kshrd.wearekhmer.Category.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {

    @NotNull(message = "Category name must be provided.")
    @NotBlank(message = "Category name must be not blank.")
    private String categoryName;
    private String categoryImage;
}
