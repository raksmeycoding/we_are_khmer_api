package com.kshrd.wearekhmer.article.service;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.response.ArticleResponse;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface ArticleService {
    List<ArticleResponse> getAllArticles();

    List<ArticleResponse> getArticlesForCurrentUser(String userId);

    Article insertArticle(Article article);
    List<ArticleResponse> getAllArticlesWithPaginate(Integer pageSize, Integer offsetValue);

    List<ArticleResponse> getArticlesForCurrentUserWithPaginate(String userId, Integer pageSize, Integer nextPage);

    ArticleResponse getArticleById(String articleId);

    ArticleResponse getArticleByIdForCurrentUser(String articleId, String currentUserId);

    List<ArticleResponse> getAllArticleByCategoryName(String categoryName, Integer pageNumber, Integer nextPage);

    Article updateArticle(Article article);

    List<ArticleResponse> getArticleByMostViewLimit20();

    Article deleteArticleByIdAndCurrentUser(Article article);

    boolean isArticleExist(String articleId);


    String increaseArticleViewCount(String articleId);

    List<ArticleResponse> getAllArticlesByYesterday();

    List<ArticleResponse> getAllArticlesByLastWeek();

    List<ArticleResponse> getAllArticlesByLastMonth();

    List<ArticleResponse> getAllArticlesByLastYear();

    List<ArticleResponse> getAllArticlesByDateRange(Date startDate, Date endDate);

    List<ArticleResponse> getAllArticleByCategoryId(String categoryId, Integer pageNumber, Integer nextPage);
}
