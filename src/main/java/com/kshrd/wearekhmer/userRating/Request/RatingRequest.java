package com.kshrd.wearekhmer.userRating.Request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
public class RatingRequest {

    @NotBlank(message = "author_id field must be not blank.")
    @NotNull(message = "author_id field must be not null.")
    private String author_id;
    @Max(value = 5, message = "Max value of number_of_rating is 5")
    private Integer number_of_rating;
}
