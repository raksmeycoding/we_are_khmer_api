package com.kshrd.wearekhmer.userReviewAuthor.service;

import com.kshrd.wearekhmer.userReviewAuthor.model.entity.UserReviewAuthor;
import com.kshrd.wearekhmer.userReviewAuthor.model.response.UserReviewAuthorResponse;

import java.util.List;

public interface IUserReviewAuthorService {
    UserReviewAuthor insertUserReviewAuthorByCurrentUserId(UserReviewAuthor userReviewAuthor);
    List<UserReviewAuthorResponse> getAllUserReviewAuthorByAuthorId(String authorId);
}
