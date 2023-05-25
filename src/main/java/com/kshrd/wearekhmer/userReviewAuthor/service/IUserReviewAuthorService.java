package com.kshrd.wearekhmer.userReviewAuthor.service;

import com.kshrd.wearekhmer.userReviewAuthor.model.entity.UserReviewAuthor;

import java.util.List;

public interface IUserReviewAuthorService {
    UserReviewAuthor insertUserReviewAuthorByCurrentUserId(UserReviewAuthor userReviewAuthor);
    List<UserReviewAuthor> getAllUserReviewAuthorByAuthorId(String authorId);
}
