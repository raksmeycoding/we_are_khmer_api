package com.kshrd.wearekhmer.userArtivities.service;


import com.kshrd.wearekhmer.userArtivities.model.UserComment;
import com.kshrd.wearekhmer.userArtivities.model.dto.AuthorReplyCommentMapper;
import com.kshrd.wearekhmer.userArtivities.repository.ICommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements ICommentService{
    private final ICommentRepository commentRepository;

    @Override
    public List<UserComment> getUserCommentByArticleId(String articleId) {
        return commentRepository.getUserCommentByArticleId(articleId);
    }

    @Override
    public UserComment creatArticleComment(String user_id, String article_id, String comment) {
        return commentRepository.creatArticleComment(user_id, article_id, comment);
    }


    @Override
    public UserComment authorReplyCommentToHisArticle(AuthorReplyCommentMapper authorReplyCommentMapper) {
        return commentRepository.authorReplyCommentToHisArticle(authorReplyCommentMapper);
    }


    @Override
    public boolean validateParentIdExist(String parentId) {
        return commentRepository.validateParentIdExist(parentId);
    }

    @Override
    public Integer countComment(String articleId) {
        return commentRepository.countComment(articleId);
    }
}
