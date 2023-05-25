package com.kshrd.wearekhmer.userRating.Request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RatingRequest {
    private String author_id;
    private Integer number_of_rating;
}
