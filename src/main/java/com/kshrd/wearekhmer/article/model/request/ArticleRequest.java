package com.kshrd.wearekhmer.article.model.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {
    @NotBlank(message = "Article must be not blank.")
    @NotNull(message = "Article must be not Null.")
    private String title;


    @NotBlank(message = "Subtitle must be not blank.")
    @NotNull(message = "Subtitle must be not Null.")
    private String subTitle;


    @NotBlank(message = "Subtitle must be not blank.")
    @NotNull(message = "Subtitle must be not Null.")
    private String description;


    private String articleImage;


    @NotBlank(message = "Category Id must be not blank.")
    @NotNull(message = "Category Id must be not null.")
    private String categoryId;
}
