package com.kshrd.wearekhmer.userArtivities.service;

import com.kshrd.wearekhmer.userArtivities.model.UserComment;

import java.util.List;

public interface ICommentService {
    List<UserComment> getUserCommentByArticleId(String articleId);
    UserComment creatArticleComment(String user_id, String article_id, String comment);
}
