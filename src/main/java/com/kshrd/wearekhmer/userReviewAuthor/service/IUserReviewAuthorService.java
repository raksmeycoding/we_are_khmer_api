package com.kshrd.wearekhmer.userReviewAuthor.service;

import com.kshrd.wearekhmer.userReviewAuthor.model.entity.UserReviewAuthor;

public interface IUserReviewAuthorService {
    UserReviewAuthor insertUserReviewAuthorByCurrentUserId(UserReviewAuthor userReviewAuthor);
}
