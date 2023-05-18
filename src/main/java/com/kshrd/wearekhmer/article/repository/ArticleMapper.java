package com.kshrd.wearekhmer.article.repository;

import com.kshrd.wearekhmer.article.model.entity.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ArticleMapper {
    @Results(id = "articleResultMap", value = {
            @Result(property = "articleId", column = "article_id"),
            @Result(property = "title", column = "title"),
            @Result(property = "subTitle", column = "sub_title"),
            @Result(property = "publishDate", column = "publish_date"),
            @Result(property = "description", column = "description"),
            @Result(property = "updateAt", column = "updatedAt"),
            @Result(property = "image", column = "image"),
            @Result(property = "countView", column = "count_view"),
            @Result(property = "isBan", column = "isBan"),
            @Result(property = "heroCardIn", column = "hero_card_in"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "categoryId", column = "category_id")
    })
    @Select("SELECT * FROM article_tb")
    List<Article> getAllArticles();

    @ResultMap("articleResultMap")
    @Select("SELECT * FROM article_tb WHERE article_id = #{articleId}")
    Article getArticleById(String articleId);

    @Select("INSERT INTO article_tb (title, sub_title, description, image, user_id, category_id) " +
            "VALUES (#{title}, #{subTitle}, #{description}, #{image}, #{userId}, #{categoryId}) returning *")
    @ResultMap("articleResultMap")
    Article insertArticle(Article article);

    @Update("UPDATE article_tb SET title = #{title}, sub_title = #{subTitle}, publish_date = #{publishDate}, description = #{description}, " +
            "updatedAt = #{updateAt}, image = #{image}, count_view = #{countView}, isBan = #{isBan}, hero_card_in = #{heroCardIn}, " +
            "user_id = #{userId}, category_id = #{categoryId} WHERE article_id = #{articleId}")
    void updateArticle(Article article);

    @Delete("DELETE FROM article_tb WHERE article_id = #{articleId}")
    void deleteArticle(String articleId);
}
