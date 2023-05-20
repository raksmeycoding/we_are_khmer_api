package com.kshrd.wearekhmer.article.service;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.model.request.ArticleUpdateRequest;
import com.kshrd.wearekhmer.article.repository.ArticleMapper;
import com.kshrd.wearekhmer.article.response.ArticleResponse;
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
    public List<ArticleResponse> getAllArticlesForCurrentUser(String userId) {
        return articleMapper.getArticleForCurrentUser(userId);
    }

    @Override
    public Article getArticleById(String articleId) {
        return articleMapper.getArticleById(articleId);
    }

    @Override
    public Article insertArticle(Article article) {
        return articleMapper.insertArticle(article);
    }


    @Override
    public Article updateArticle(Article article) {
        return articleMapper.updateArticle(article);
    }

    @Override
    public Article deleteArticleByIdAndCurrentUser(Article article) {
        return articleMapper.deleteArticleByIdAndCurrentUser(article);
    }
}
