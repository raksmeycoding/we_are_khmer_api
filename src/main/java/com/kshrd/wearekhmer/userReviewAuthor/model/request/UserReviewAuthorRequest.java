package com.kshrd.wearekhmer.userReviewAuthor.model.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserReviewAuthorRequest {

    @NotBlank(message = "author_id must be not blank.")
    @NotNull(message = "author_id  must be not null.")
    private String author_id;

    @NotBlank(message = "comment must be not blank.")
    @NotNull(message = "comment  must be not null.")
    private String comment;
}
