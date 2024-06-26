package com.kshrd.wearekhmer.userReviewAuthor.service;

import com.kshrd.wearekhmer.userReviewAuthor.model.entity.UserReviewAuthor;
import com.kshrd.wearekhmer.userReviewAuthor.model.response.UserReviewAuthorResponse;
import com.kshrd.wearekhmer.userReviewAuthor.repository.UserReviewAuthorMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserReviewAuthorImplService implements IUserReviewAuthorService {
    private final UserReviewAuthorMapper userReviewAuthorMapper;
    @Override
    public UserReviewAuthor insertUserReviewAuthorByCurrentUserId(UserReviewAuthor userReviewAuthor) {
        return userReviewAuthorMapper.insertUserReviewAuthorByCurrentUserId(userReviewAuthor);
    }


    @Override
    public List<UserReviewAuthorResponse> getAllUserReviewAuthorByAuthorId(String authorId) {
        return userReviewAuthorMapper.getAllUserReviewAuthorByAuthorId(authorId);
    }
}
