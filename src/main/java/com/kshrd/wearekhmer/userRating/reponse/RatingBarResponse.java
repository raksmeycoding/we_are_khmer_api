package com.kshrd.wearekhmer.userRating.reponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingBarResponse {
    private String rating_name;
    private Integer rating_number;
    private Integer rating_count;
}
