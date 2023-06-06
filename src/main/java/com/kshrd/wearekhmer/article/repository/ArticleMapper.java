package com.kshrd.wearekhmer.article.repository;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.response.ArticleResponse;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
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
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
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
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
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
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
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
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
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
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
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
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
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
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
            where ab.article_id = #{articleId} and ub.user_id = #{currentUserId}
            """)
    ArticleResponse getArticleByIdForCurrentUser(String articleId, String currentUserId);

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


    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
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
            select count(*) from article_tb  as article_count where user_id = #{userId}
            """)
    Integer getTotalRecordOfArticleForCurrentUser(String userId);

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
            WHERE  publish_date :: date = current_date -1 AND isban = false
            ORDER BY publish_date DESC limit #{pageNumber} offset #{nextPage}
            """)
    List<ArticleResponse> getAllArticlesByYesterday(Integer pageNumber, Integer nextPage);

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
            WHERE DATE_TRUNC('week', publish_date) = DATE_TRUNC('week', current_date)
            AND isban = false
            ORDER BY publish_date DESC limit #{pageNumber} offset #{nextPage}
            """)
    List<ArticleResponse> getAllArticlesPerWeek(Integer pageNumber, Integer nextPage);

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
            WHERE EXTRACT(MONTH FROM publish_date) = EXTRACT(MONTH FROM current_date)
            AND EXTRACT(YEAR FROM publish_date) = EXTRACT(YEAR FROM current_date)
            AND isban = false 
            ORDER BY publish_date DESC limit #{pageNumber} offset #{nextPage}
            """)
    List<ArticleResponse> getAllArticlesPerMonth(Integer pageNumber, Integer nextPage);

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
            WHERE date_trunc('year', publish_date) = date_trunc('year', current_date)
            AND EXTRACT(YEAR FROM publish_date) = EXTRACT(YEAR FROM current_date)
            AND isban = false 
            ORDER BY publish_date DESC limit #{pageNumber} offset #{nextPage}
            """)
    List<ArticleResponse> getAllArticlesPerYear(Integer pageNumber, Integer nextPage);

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
                   ub.username as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
            WHERE publish_date >= #{startDate} AND publish_date <= #{endDate}
            AND isban = false 
            ORDER BY publish_date DESC 
            """)
    List<ArticleResponse> getAllArticlesByDateRange(Date startDate, java.sql.Date endDate);

    @Select("""
            select exists(select 1 from article_tb where article_id = #{articleId} and user_id = #{userId})
            """)
    boolean validateArticleIdByCurrentUser(String articleId, String userId);

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
                   ub.username                                                                as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
            from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
            where c.category_id = #{categoryId} limit #{pageNumber} offset #{nextPage}
            """)
    List<ArticleResponse> getAllArticleByCategoryId(String categoryId, Integer pageNumber, Integer nextPage);


    @Select("""
            select exists(select 1 from category where category_name = #{categoryName} )
            """)
    Boolean isCategoryNameExists(String categoryName);

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
                   ub.username                                                                as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
            from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
            where ab.user_id = #{userId} AND isBan = false AND is_author = true order by count_view desc limit #{pageNumber} offset #{nextPage}
                        """)
    List<ArticleResponse> getAllArticleCurrentUserByMostView(String userId, Integer pageNumber, Integer nextPage);

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
                   ub.username                                                                as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
            from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
            where ab.user_id = #{userId} AND isBan = false AND is_author = true ORDER BY ab.publish_date desc limit #{pageNumber} offset #{nextPage}
                        """)
    List<ArticleResponse> getAllArticleCurrentUserByLatest(String userId, Integer pageNumber, Integer nextPage);

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
                   ub.username                                                                as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
            from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
            where publish_date :: date = (current_date -1) AND ab.user_id = #{userId} AND isBan = false AND is_author = true  ORDER BY ab.publish_date desc limit #{pageNumber} offset #{nextPage}
                        """)
    List<ArticleResponse> getAllArticleCurrentUserByYesterday(String userId, Integer pageNumber, Integer nextPage);

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
                   ub.username                                                                as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
            from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
            where date_trunc('week', publish_date) = date_trunc('week', current_date) AND ab.user_id = #{userId} AND isBan = false AND is_author = true  ORDER BY ab.publish_date desc limit #{pageNumber} offset #{nextPage}
                        """)
    List<ArticleResponse> getAllArticleCurrentUserPerWeek(String userId, Integer pageNumber, Integer nextPage);

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
                   ub.username                                                                as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
            from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
            where EXTRACT(MONTH FROM publish_date) = EXTRACT(MONTH FROM current_date)
                    AND EXTRACT(YEAR FROM publish_date) = EXTRACT(YEAR FROM current_date) AND ab.user_id = #{userId} AND isBan = false AND is_author = true  ORDER BY ab.publish_date desc limit #{pageNumber} offset #{nextPage}
                        """)
    List<ArticleResponse> getAllArticleCurrentUserPerMonth(String userId, Integer pageNumber, Integer nextPage);

    @Select("""
            select ab.article_id,
                   ab.user_id,
                   ab.category_id,
                   ab.title,
                   ab.sub_title,
                   ab.publish_date,
                   ab.description,
                   ab.updatedat,
                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
                   ab.count_view,
                   ab.isban,
                   ab.hero_card_in,
                   ub.photo_url,
                   ub.username                                                                as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
            from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
            where date_trunc('year', publish_date) = date_trunc('year', current_date)
                    AND EXTRACT(YEAR FROM publish_date) = EXTRACT(YEAR FROM current_date) AND ab.user_id = #{userId} AND isBan = false AND is_author = true  ORDER BY ab.publish_date desc limit #{pageNumber} offset #{nextPage}
                        """)
    List<ArticleResponse> getAllArticleCurrentUserPerYear(String userId, Integer pageNumber, Integer nextPage);


    @Select("""
            SELECT COALESCE(SUM(count_view),0) AS total_views
            FROM article_tb
            WHERE DATE_TRUNC('week', publish_date) = DATE_TRUNC('week', current_date) AND user_id = #{userId}
            """)
    Integer getTotalViewCurrentAuthorPerWeek(String userId);

    @Select("""
            SELECT COALESCE(SUM(count_view),0) AS total_views
            FROM article_tb
            WHERE EXTRACT(MONTH FROM publish_date) = EXTRACT(MONTH FROM current_date)
              AND EXTRACT(YEAR FROM publish_date) = EXTRACT(YEAR FROM current_date) AND user_id = #{userId}
            """)
    Integer getTotalViewCurrentAuthorPerMonth(String userId);

    @Select("""
            SELECT COALESCE(SUM(count_view),0) AS total_views
            FROM article_tb
            WHERE date_trunc('year', publish_date) = date_trunc('year', current_date)
                    AND EXTRACT(YEAR FROM publish_date) = EXTRACT(YEAR FROM current_date) AND user_id = #{userId}
            """)
    Integer getTotalViewCurrentAuthorPerYear(String userId);

    @Select("""
            SELECT COALESCE(SUM(count_view),0) AS total_views
            FROM article_tb
            WHERE DATE_TRUNC('week', publish_date) = DATE_TRUNC('week', current_date)
            """)
    Integer getTotalViewAdminPerWeek();

    @Select("""
            SELECT COALESCE(SUM(count_view),0) AS total_views
            FROM article_tb
            WHERE EXTRACT(MONTH FROM publish_date) = EXTRACT(MONTH FROM current_date)
              AND EXTRACT(YEAR FROM publish_date) = EXTRACT(YEAR FROM current_date);
            """)
    Integer getTotalViewAdminPerMonth();

    @Select("""
            SELECT COALESCE(SUM(count_view),0) AS total_views FROM article_tb
            WHERE date_trunc('year', publish_date) = date_trunc('year', current_date)
            AND EXTRACT(YEAR FROM publish_date) = EXTRACT(YEAR FROM current_date)
            """)
    Integer getTotalViewAdminPerYear();


}
