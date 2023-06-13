package com.kshrd.wearekhmer.userRating;

import com.kshrd.wearekhmer.userRating.dto.RatingDto;
import com.kshrd.wearekhmer.userRating.reponse.RatingBarResponse;
import com.kshrd.wearekhmer.userRating.reponse.RatingResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class RatingServiceImpl implements IRatingService{

    private final RatingRepository ratingRepository;

    @Override
    public  Rating createUserRatingToAuthor(RatingDto ratingDto) {
        return ratingRepository.createUserRatingToAuthor(ratingDto);
    }

    @Override
    public RatingResponse getRatingByAuthorId(String authorId) {
        return ratingRepository.getRatingByAuthorId(authorId);
    }


    @Override
    public List<RatingBarResponse> getRatingBarByAuthorId(String authorId) {
        return ratingRepository.getRatingBarByAuthorId(authorId);
    }


    @Override
    public Integer getTotalViewAllRecordsByAuthorId(String authorId) {
        return ratingRepository.getTotalViewAllRecordsByAuthorId(authorId);
    }
}
