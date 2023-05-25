package com.kshrd.wearekhmer.userRating.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class RatingDto {
    private String user_id;
    private String author_id;
    private Integer number_of_rating;
}
