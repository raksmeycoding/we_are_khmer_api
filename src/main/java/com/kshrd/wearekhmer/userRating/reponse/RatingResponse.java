package com.kshrd.wearekhmer.userRating.reponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RatingResponse {
    private Integer  sum_rating_number;
    private String username;
    private String photo_url;

}
