package com.kshrd.wearekhmer.heroCard.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeroCardRequest {
    private Integer index;
    @NotNull(message = "category_id must be not null.")
    @NotBlank(message = "category_id must be not blank.")
    private String categoryId;

    @NotNull(message = "article_id must be not null.")
    @NotBlank(message = "article_id  must be not blank.")
    private String articleId;

    @NotNull(message = "type must be not null.")
    @NotBlank(message = "type  must be not blank.")
    private String type;
}
