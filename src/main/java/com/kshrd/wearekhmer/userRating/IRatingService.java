package com.kshrd.wearekhmer.userRating;

import com.kshrd.wearekhmer.userRating.dto.RatingDto;
import com.kshrd.wearekhmer.userRating.reponse.RatingResponse;

public interface IRatingService {
    Rating createUserRatingToAuthor(RatingDto ratingDto);

    RatingResponse getRatingByAuthorId(String authorId);
}
