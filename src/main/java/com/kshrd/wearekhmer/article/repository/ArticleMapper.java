package com.kshrd.wearekhmer.article.repository;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.response.ArticleResponse;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.sql.Timestamp;
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
                   concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
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
                   concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id limit #{pageSize} offset #{offsetValue};
            """)
    List<ArticleResponse> getAllArticlesWithPaginate(Integer pageSize, Integer offsetValue);


    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.username                                                                as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
            from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
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
                   concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.username                                                                as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
            from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
            where ab.user_id = #{userId} limit #{pageSize} offset #{nextPage}
            """)
    List<ArticleResponse> getArticlesForCurrentUserWithPaginate(String userId, Integer pageSize, Integer nextPage);


    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.username                                                                as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
            from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
            where lower(category_name)  = lower(#{categoryName}) limit #{pageNumber} offset #{nextPage}
            """)
    List<ArticleResponse> getAllArticleByCategoryName(String categoryName, Integer pageNumber, Integer nextPage);


    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
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
            where ab.article_id = #{articleId} and ub.user_id = #{currentUserId}
            """)
    ArticleResponse getArticleByIdForCurrentUser(String articleId, String currentUserId);

    @Select("INSERT INTO article_tb (title, sub_title, description, user_id, category_id) " +
            "VALUES (#{title}, #{subTitle}, #{description}, #{userId}, #{categoryId}) returning *")
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


    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.username                                                                as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
            from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
            where isBan = false order by count_view desc limit 20 offset 0;
            """)
    List<ArticleResponse> getArticleByMostViewLimit20();


    @Select("""
            update article_tb set count_view = count_view + 1 where article_id = #{articleId} returning article_id;
            """)
    String increaseArticleViewCount(String articleId);


    @Select("""
            select count(*) from article_tb as article_count
            """)
    Integer getTotalRecordOfArticleTb();

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
            WHERE  publish_date :: date = current_date -1 AND isban = false
            ORDER BY publish_date DESC LIMIT 20 OFFSET 0
            """)
    List<ArticleResponse> getAllArticlesByYesterday();

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
            WHERE publish_date >= current_date - interval '6 days'
            AND publish_date < current_date + interval '1 day' 
            AND isban = false
            ORDER BY publish_date DESC LIMIT 20 OFFSET 0
            """)
    List<ArticleResponse> getAllArticlesByLastWeek();

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
            WHERE date_trunc('month', publish_date)=
            date_trunc('month', current_date - interval '1 month')
            AND isban = false 
            ORDER BY publish_date DESC LIMIT 20 OFFSET 0
            """)
    List<ArticleResponse> getAllArticlesByLastMonth();

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
            WHERE publish_date BETWEEN DATE_TRUNC('year', CURRENT_DATE - INTERVAL '1 year') AND DATE_TRUNC('year', CURRENT_DATE) - INTERVAL '1 day'
            AND isban = false 
            ORDER BY publish_date DESC LIMIT 20 OFFSET 0
            """)
    List<ArticleResponse> getAllArticlesByLastYear();

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
            WHERE publish_date >= #{startDate} AND publish_date <= #{endDate}
            AND isban = false 
            ORDER BY publish_date DESC LIMIT 20 OFFSET 0
            """)
    List<ArticleResponse> getAllArticlesByDateRange(Date startDate, java.sql.Date endDate);

    @Select("""
            select exists(select 1 from article_tb where article_id = #{articleId} and user_id = #{userId})
            """)
    boolean validateArticleIdByCurrentUser(String articleId, String userId);


}
