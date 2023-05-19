package com.kshrd.wearekhmer.article.service;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.model.request.ArticleUpdateRequest;

import java.util.List;

public interface IArticleService {
    List<Article> getAllArticles();

    List<Article> getAllArticlesForCurrentUser(String userId);
    Article insertArticle(Article article);

    Article getArticleById(String articleId);

    Article updateArticle(Article article);

    Article deleteArticleByIdAndCurrentUser(Article article);
}
