package com.kshrd.wearekhmer.userRating;

import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.userRating.dto.RatingDto;
import com.kshrd.wearekhmer.userRating.reponse.RatingBarResponse;
import com.kshrd.wearekhmer.userRating.reponse.RatingResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class RatingServiceImpl implements IRatingService{

    private final RatingRepository ratingRepository;

    @Override
    public  Rating createUserRatingToAuthor(RatingDto ratingDto) {
        if(ratingRepository.checkAlreadyRating(ratingDto.getUser_id(), ratingDto.getAuthor_id()))
            throw new ValidateException("You already rated this author", HttpStatus.FORBIDDEN,HttpStatus.FORBIDDEN.value());
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
