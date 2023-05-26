package com.kshrd.wearekhmer.article.controller;

import com.kshrd.wearekhmer.article.model.request.ArticleRequest;
import com.kshrd.wearekhmer.article.model.request.ArticleUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface IArticleController {

    ResponseEntity<?> getAllArticles();

//    ResponseEntity<?> getAllArticleForCurrentUser();

    ResponseEntity<?> getAllArticlesForCurrentUser();

    ResponseEntity<?> getAllArticleByCategoryName(String categoryName);

    ResponseEntity<?> insertArticle(ArticleRequest articleRequest);

    ResponseEntity<?> getArticleById(String articleId);

    ResponseEntity<?> getArticleByIdForCurrentUser(String articleId);

    ResponseEntity<?> updateArticle(ArticleUpdateRequest article);

    ResponseEntity<?> deleteArticle(String articleId);


    ResponseEntity<?> getArticleByMostViewLimit20();


    ResponseEntity<?> increaseArticleViewCount(String articleId);
}
