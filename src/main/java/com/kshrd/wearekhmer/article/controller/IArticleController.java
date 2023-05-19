package com.kshrd.wearekhmer.article.controller;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.model.request.ArticleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IArticleController {

    ResponseEntity<?> getAllArticles();

    ResponseEntity<?> insertArticle(MultipartFile multipartFile, ArticleRequest articleRequest);

    Article getArticleById(String articleId);
}
