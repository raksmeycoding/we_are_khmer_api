package com.kshrd.wearekhmer.userRating;

import com.kshrd.wearekhmer.userRating.dto.RatingDto;
import com.kshrd.wearekhmer.userRating.reponse.RatingBarResponse;
import com.kshrd.wearekhmer.userRating.reponse.RatingResponse;

import java.util.List;

public interface IRatingService {
    Rating createUserRatingToAuthor(RatingDto ratingDto);

    RatingResponse getRatingByAuthorId(String authorId);


    List<RatingBarResponse> getRatingBarByAuthorId(String authorId);


    Integer getTotalViewAllRecordsByAuthorId(String authorId);

}
