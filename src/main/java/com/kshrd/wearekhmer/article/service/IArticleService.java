package com.kshrd.wearekhmer.article.service;

import com.kshrd.wearekhmer.article.model.entity.Article;

import java.util.List;

public interface IArticleService {
    List<Article> getAllArticles();
    Article insertArticle(Article article);
}
