package com.kshrd.wearekhmer.article.service;

import com.kshrd.wearekhmer.article.model.Response.ArticleResponse2;
import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.response.ArticleResponse;

import java.sql.Date;
import java.util.List;

public interface ArticleService {
    List<ArticleResponse2> getAllArticlesByLatest(Integer pageSize, Integer nextPage, String userId);

    List<ArticleResponse> getArticlesForCurrentUser(String userId);

    Article insertArticle(Article article);

    List<ArticleResponse> getAllArticlesWithPaginate(Integer pageSize, Integer offsetValue);

    List<ArticleResponse> getArticlesForCurrentUserWithPaginate(String userId, Integer pageSize, Integer nextPage);

    ArticleResponse2 getArticleById(String articleId, String userId);

    ArticleResponse2 getArticleByIdForCurrentUser(String articleId, String currentUserId);

    List<ArticleResponse> getAllArticleByCategoryName(String categoryName, Integer pageNumber, Integer nextPage);

    Article updateArticle(Article article);

    List<ArticleResponse> getArticleByMostViewLimit20();

    Article deleteArticleByIdAndCurrentUser(Article article);

    boolean isArticleExist(String articleId);


    String increaseArticleViewCount(String articleId);

    List<ArticleResponse> getAllArticlesByYesterday(Integer pageNumber, Integer nextPage);

    List<ArticleResponse> getAllArticlesPerWeek(Integer pageNumber, Integer nextPage);

    List<ArticleResponse> getAllArticlesPerMonth(Integer pageNumber, Integer nextPage);

    List<ArticleResponse> getAllArticlesPerYear(Integer pageNumber, Integer nextPage);

    List<ArticleResponse> getAllArticlesByDateRange(Date startDate, Date endDate);

    List<ArticleResponse2> getAllArticleByCategoryId(String categoryId, Integer pageNumber, Integer nextPage, String userId);

    List<ArticleResponse2> getAllArticleCurrentUserByMostView(String userId, Integer pageNumber, Integer nextPage);

    List<ArticleResponse2> getAllArticleCurrentUserByLatest(String userId, Integer pageNumber, Integer nextPage);

    List<ArticleResponse2> getAllArticleCurrentUserByYesterday(String userId, Integer pageNumber, Integer nextPage);

    List<ArticleResponse2> getAllArticleCurrentUserPerWeek(String userId, Integer pageNumber, Integer nextPage);

    List<ArticleResponse2> getAllArticleCurrentUserPerMonth(String userId, Integer pageNumber, Integer nextPage);

    List<ArticleResponse2> getAllArticleCurrentUserPerYear(String userId, Integer pageNumber, Integer nextPage);

    Integer getTotalViewCurrentAuthorPerWeek(String userId);

    Integer getTotalViewCurrentAuthorPerMonth(String userId);

    Integer getTotalViewCurrentAuthorPerYear(String userId);

    Integer getTotalViewAdminPerWeek();

    Integer getTotalViewAdminPerMonth();

    Integer getTotalViewAdminPerYear();


    //    List<ArticleResponse> filterArticles(String title, String categoryId);
    boolean adminBanArticle(String articleId);
}
