package com.kshrd.wearekhmer.article.service;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.response.ArticleResponse;

import java.util.List;

public interface IArticleService {
    List<ArticleResponse> getAllArticles();

    List<ArticleResponse> getArticlesForCurrentUser(String userId);

    Article insertArticle(Article article);

    ArticleResponse getArticleById(String articleId);

    ArticleResponse getArticleByIdForCurrentUser(String articleId, String currentUserId);

    List<ArticleResponse> getAllArticleByCategoryName(String categoryName);

    Article updateArticle(Article article);

    Article deleteArticleByIdAndCurrentUser(Article article);

    boolean isArticleExist(String articleId);
}
