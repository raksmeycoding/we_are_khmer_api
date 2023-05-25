package com.kshrd.wearekhmer.article.repository;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.response.ArticleResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ArticleMapper {

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   ab.image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id;
            """)
    List<ArticleResponse> getAllArticles();


    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   ab.image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
            where ab.user_id = #{userId}
            """)
    List<ArticleResponse> getArticlesForCurrentUser(String userId);


    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   ab.image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
            where ab.article_id = #{articleId}
            """)
    ArticleResponse getArticleById(String articleId);

    @Select("INSERT INTO article_tb (title, sub_title, description, image, user_id, category_id) " +
            "VALUES (#{title}, #{subTitle}, #{description}, #{image}, #{userId}, #{categoryId}) returning *")
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
    Article insertArticle(Article article);

    @Select("""
            UPDATE article_tb SET title = #{title}, sub_title = #{subTitle}, description = #{description}, category_id = #{categoryId} WHERE article_id = #{articleId} and user_id = #{userId} returning *
            """)
    @ResultMap("articleResultMap")
    Article updateArticle(Article article);

    @Select("DELETE FROM article_tb WHERE article_id = #{articleId} and user_id = #{userId} returning *")
    @ResultMap("articleResultMap")
    Article deleteArticleByIdAndCurrentUser(Article article);


    @Select("""
            select exists(select 1 from article_tb where article_tb.article_id = #{articleId})
            """)
    boolean isArticleExist(String articleId);
}
