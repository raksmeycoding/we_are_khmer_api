package com.kshrd.wearekhmer.userArtivities.model.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentRequest {


    @NotBlank(message = "articleId field must be not blank.")
    @NotNull(message = "articleId field must be not null.")
    private String articleId;

    @NotBlank(message = "comment field Id must be not blank.")
    @NotNull(message = "comment field must be not null.")
    private String comment;
}
