package com.kshrd.wearekhmer.article.controller;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.model.request.ArticleRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IArticleController {

    ResponseEntity<?> getAllArticles();

    ResponseEntity<?> insertArticle(ArticleRequest articleRequest);
}
