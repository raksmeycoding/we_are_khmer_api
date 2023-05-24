package com.kshrd.wearekhmer.article.service;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.model.request.ArticleUpdateRequest;
import com.kshrd.wearekhmer.article.response.ArticleResponse;

import java.util.List;

public interface IArticleService {
    List<Article> getAllArticles();

    List<ArticleResponse> getArticlesForCurrentUser(String userId);

    Article insertArticle(Article article);

    ArticleResponse getArticleById(String articleId);

    Article updateArticle(Article article);

    Article deleteArticleByIdAndCurrentUser(Article article);

    boolean isArticleExist(String articleId);
}
