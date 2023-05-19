package com.kshrd.wearekhmer.article.service;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.repository.ArticleMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ArticleServiceImpl implements IArticleService{

    private final ArticleMapper articleMapper;


    @Override
    public List<Article> getAllArticles() {
        return articleMapper.getAllArticles();

    }


    @Override
    public Article getArticleById(String articleId) {
        return articleMapper.getArticleById(articleId);
    }

    @Override
    public Article insertArticle(Article article) {
        return articleMapper.insertArticle(article);
    }
}
