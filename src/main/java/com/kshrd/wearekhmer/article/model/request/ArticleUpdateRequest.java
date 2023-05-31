package com.kshrd.wearekhmer.article.model.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleUpdateRequest {
    private String articleId;


    @NotBlank(message = "Title must be not blank.")
    @NotNull(message = "Title must be not Null.")
    private String title;
    @NotBlank(message = "Subtitle must be not blank.")
    @NotNull(message = "Subtitle must be not Null.")
    private String subTitle;
    @NotBlank(message = "Description must be not blank.")
    @NotNull(message = "Description must be not Null.")
    private String description;
    private String categoryId;
}
