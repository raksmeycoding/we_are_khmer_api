package com.kshrd.wearekhmer.article.service;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.repository.ArticleMapper;
import com.kshrd.wearekhmer.article.response.ArticleResponse;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


@Service
@AllArgsConstructor
public class ArticleServiceImp implements ArticleService {

    private final ArticleMapper articleMapper;




    @Override
    public List<ArticleResponse> getAllArticlesByLatest(Integer pageSize, Integer nextPage) {
        return articleMapper.getAllArticlesByLatest(pageSize, nextPage);

    }


    @Override
    public List<ArticleResponse> getAllArticlesWithPaginate(Integer pageSize, Integer offsetValue) {
        return articleMapper.getAllArticlesWithPaginate(pageSize, offsetValue);
    }

    @Override
    public List<ArticleResponse> getArticlesForCurrentUser(String userId) {
        return articleMapper.getArticlesForCurrentUser(userId);
    }


    @Override
    public List<ArticleResponse> getArticlesForCurrentUserWithPaginate(String userId, Integer pageSize, Integer nextPage) {
        return articleMapper.getArticlesForCurrentUserWithPaginate(userId, pageSize, nextPage);
    }

    @Override
    public List<ArticleResponse> getAllArticleByCategoryName(String categoryName, Integer pageNumber, Integer nextPage) {
        return articleMapper.getAllArticleByCategoryName(categoryName, pageNumber, nextPage);
    }

    @Override
    public ArticleResponse getArticleById(String articleId) {
        return articleMapper.getArticleById(articleId);
    }

    @Override
    public ArticleResponse getArticleByIdForCurrentUser(String articleId, String currentUserId) {
        return articleMapper.getArticleByIdForCurrentUser(articleId, currentUserId);
    }

    @Override
    public Article insertArticle(Article article) {
//        if(article.getTitle().isBlank())
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"title field must not be blank");
//        else if(article.getSubTitle().isBlank())
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"subTitle field must not be blank");
//        else if(article.getDescription().isBlank())
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "description field must not be blank");

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


    @Override
    public boolean isArticleExist(String articleId) {
        return articleMapper.isArticleExist(articleId);
    }


    @Override
    public List<ArticleResponse> getArticleByMostViewLimit20() {
        return articleMapper.getArticleByMostViewLimit20();
    }


    @Override
    public String increaseArticleViewCount(String articleId) {
        return articleMapper.increaseArticleViewCount(articleId);
    }

    @Override
    public List<ArticleResponse> getAllArticlesByYesterday(Integer pageNumber, Integer nextPage) {
        return articleMapper.getAllArticlesByYesterday(pageNumber, nextPage);
    }

    @Override
    public List<ArticleResponse> getAllArticlesPerWeek(Integer pageNumber, Integer nextPage) {
        return articleMapper.getAllArticlesPerWeek(pageNumber, nextPage);
    }

    @Override
    public List<ArticleResponse> getAllArticlesPerMonth(Integer pageNumber, Integer nextPage) {
        return articleMapper.getAllArticlesPerMonth(pageNumber, nextPage);
    }

    @Override
    public List<ArticleResponse> getAllArticlesPerYear(Integer pageNumber, Integer nextPage) {
        return articleMapper.getAllArticlesPerYear(pageNumber, nextPage);
    }

    @Override
    public List<ArticleResponse> getAllArticlesByDateRange(Date startDate, Date endDate) {
        return articleMapper.getAllArticlesByDateRange(startDate,endDate);
    }

    @Override
    public List<ArticleResponse> getAllArticleByCategoryId(String categoryId, Integer pageNumber, Integer nextPage) {
        return articleMapper.getAllArticleByCategoryId(categoryId, pageNumber,nextPage);
    }

    @Override
    public List<ArticleResponse> getAllArticleCurrentUserByMostView(String userId, Integer pageNumber, Integer nextPage) {
        return articleMapper.getAllArticleCurrentUserByMostView(userId, pageNumber, nextPage);
    }

    @Override
    public List<ArticleResponse> getAllArticleCurrentUserByLatest(String userId, Integer pageNumber, Integer nextPage) {
        return articleMapper.getAllArticleCurrentUserByLatest(userId, pageNumber, nextPage);
    }

    @Override
    public List<ArticleResponse> getAllArticleCurrentUserByYesterday(String userId, Integer pageNumber, Integer nextPage) {
        return articleMapper.getAllArticleCurrentUserByYesterday(userId, pageNumber, nextPage);
    }

    @Override
    public List<ArticleResponse> getAllArticleCurrentUserPerWeek(String userId, Integer pageNumber, Integer nextPage) {
        return articleMapper.getAllArticleCurrentUserPerWeek(userId, pageNumber, nextPage);
    }

    @Override
    public List<ArticleResponse> getAllArticleCurrentUserPerMonth(String userId, Integer pageNumber, Integer nextPage) {
        return articleMapper.getAllArticleCurrentUserPerMonth(userId, pageNumber, nextPage);

    }

    @Override
    public List<ArticleResponse> getAllArticleCurrentUserPerYear(String userId, Integer pageNumber, Integer nextPage) {
        return articleMapper.getAllArticleCurrentUserPerYear(userId,pageNumber,nextPage);
    }

    @Override
    public Integer getTotalViewCurrentAuthorPerWeek(String userId) {
        return articleMapper.getTotalViewCurrentAuthorPerWeek(userId);
    }

    @Override
    public Integer getTotalViewCurrentAuthorPerMonth(String userId) {
        return articleMapper.getTotalViewCurrentAuthorPerMonth(userId);
    }

    @Override
    public Integer getTotalViewCurrentAuthorPerYear(String userId) {
        return articleMapper.getTotalViewCurrentAuthorPerYear(userId);
    }

    @Override
    public Integer getTotalViewAdminPerWeek() {
        return articleMapper.getTotalViewAdminPerWeek();
    }

    @Override
    public Integer getTotalViewAdminPerMonth() {
        return articleMapper.getTotalViewAdminPerMonth();
    }

    @Override
    public Integer getTotalViewAdminPerYear() {
        return articleMapper.getTotalViewAdminPerYear();
    }
}
