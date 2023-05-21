package com.kshrd.wearekhmer.userArtivities.controller;

import com.kshrd.wearekhmer.userArtivities.model.UserComment;
import com.kshrd.wearekhmer.userArtivities.model.request.CommentRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICommentController {
    ResponseEntity<?> getUserCommentByArticleId(String articleId);

    ResponseEntity<?> creatArticleComment(CommentRequest commentRequest);
}
