package com.kshrd.wearekhmer.article.repository;

import com.kshrd.wearekhmer.article.model.Response.ArticleResponse2;
import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.article.response.ArticleResponse;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Mapper
public interface ArticleMapper {

//    @Select("""
//            select ab.article_id,
//                   ab.user_id,
//                   ab.category_id,
//                   ab.title,
//                   ab.sub_title,
//                   ab.publish_date,
//                   ab.description,
//                   ab.updatedat,
//                   coalesce((nullif(ab.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image,
//                   ab.count_view,
//                   ab.isban,
//                   ab.hero_card_in,
//                   ub.photo_url,
//                   ub.username as author_name,
//                   c.category_name,
//                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
//            from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
//            ORDER BY publish_date DESC limit #{pageSize} offset #{nextPage};
//
//            """)

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
                           (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count, (CASE WHEN bt.user_id = #{userId} THEN true ELSE false END) AS bookmarked, (CASE WHEN rt.status = true THEN true ELSE false END) AS reacted from article_tb ab
                            inner join user_tb ub on ab.user_id = ub.user_id
                            inner join category c on c.category_id = ab.category_id
                            left outer join bookmark_tb bt on ab.article_id = bt.article_id AND bt.user_id = #{userId}
                            left outer join react_tb rt on ab.article_id = rt.article_id AND rt.user_id = #{userId} where isban = false
                            ORDER BY publish_date DESC limit #{pageSize} offset #{nextPage};
                """)
    List<ArticleResponse2> getAllArticlesByLatest(Integer pageSize, Integer nextPage, @Param("userId") String userId);


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
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count, (CASE WHEN bt.user_id = #{userId} THEN true ELSE false END) AS bookmarked, (CASE WHEN rt.status = true THEN true ELSE false END) AS reacted from article_tb ab
                    from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id 
                    left outer join bookmark_tb bt on ab.article_id = bt.article_id AND bt.user_id = #{userId}
                    left outer join react_tb rt on ab.article_id = rt.article_id AND rt.user_id = #{userId} 
                    
            limit #{pageSize} offset #{offsetValue};
            """)
    List<ArticleResponse> getAllArticlesWithPaginate(Integer pageSize, Integer nextPage);


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
                   ub.username                                                                as author_name,
                   c.category_name,
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count, 
                   (CASE WHEN bt.user_id = #{userId} THEN true ELSE false END) AS bookmarked, (CASE WHEN rt.status = true THEN true ELSE false END) AS reacted from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
                     left outer join bookmark_tb bt on ab.article_id = bt.article_id AND bt.user_id = #{userId}
                     left outer join react_tb rt on ab.article_id = rt.article_id AND rt.user_id = #{userId}
            where ab.article_id = #{articleId}
            """)
    ArticleResponse2 getArticleById(String articleId, String userId);




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
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count, 
                   (CASE WHEN bt.user_id = #{currentUserId} THEN true ELSE false END) AS bookmarked, (CASE WHEN rt.status = true THEN true ELSE false END) AS reacted from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
                     left outer join bookmark_tb bt on ab.article_id = bt.article_id AND bt.user_id = #{currentUserId}
                     left outer join react_tb rt on ab.article_id = rt.article_id AND rt.user_id = #{currentUserId}
            where ab.article_id = #{articleId} and ub.user_id = #{currentUserId}
            """)
    ArticleResponse2 getArticleByIdForCurrentUser(String articleId, String currentUserId);

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
            UPDATE article_tb SET title = #{title}, sub_title = #{subTitle}, description = #{description}, image = #{image}, category_id = #{categoryId} WHERE article_id = #{articleId} and user_id = #{userId} returning *
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
            select count(*) from article_tb a inner join category c on a.category_id = c.category_id inner join user_tb u on u.user_id = a.user_id and a.isban = false;
            """)
    Integer getTotalRecordOfArticleTb();



    static Integer getTotalRecord() {
        return getTotalRecord();
    }


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
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count, (CASE WHEN bt.user_id = #{userId} THEN true ELSE false END) AS bookmarked, (CASE WHEN rt.status = true THEN true ELSE false END) AS reacted from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
                     left outer join bookmark_tb bt on ab.article_id = bt.article_id AND bt.user_id = #{userId}
                     left outer join react_tb rt on ab.article_id = rt.article_id AND rt.user_id = #{userId}
            where c.category_id = #{categoryId} limit #{pageNumber} offset #{nextPage}
            """)
    List<ArticleResponse2> getAllArticleByCategoryId(String categoryId, Integer pageNumber, Integer nextPage, String userId);


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
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count, (CASE WHEN bt.user_id = #{userId} THEN true ELSE false END) AS bookmarked, (CASE WHEN rt.status = true THEN true ELSE false END) AS reacted from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
                     left outer join bookmark_tb bt on ab.article_id = bt.article_id AND bt.user_id = #{userId}
                     left outer join react_tb rt on ab.article_id = rt.article_id AND rt.user_id = #{userId}
            where ab.user_id = #{userId} AND isBan = false AND is_author = true order by count_view desc limit #{pageNumber} offset #{nextPage}
                        """)
    List<ArticleResponse2> getAllArticleCurrentUserByMostView(String userId, Integer pageNumber, Integer nextPage);

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
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count, (CASE WHEN bt.user_id = #{userId} THEN true ELSE false END) AS bookmarked, (CASE WHEN rt.status = true THEN true ELSE false END) AS reacted from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
                     left outer join bookmark_tb bt on ab.article_id = bt.article_id AND bt.user_id = #{userId}
                     left outer join react_tb rt on ab.article_id = rt.article_id AND rt.user_id = #{userId}
            where ab.user_id = #{userId} AND isBan = false AND is_author = true ORDER BY ab.publish_date desc limit #{pageNumber} offset #{nextPage}
                        """)
    List<ArticleResponse2> getAllArticleCurrentUserByLatest(String userId, Integer pageNumber, Integer nextPage);

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
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count, (CASE WHEN bt.user_id = #{userId} THEN true ELSE false END) AS bookmarked, (CASE WHEN rt.status = true THEN true ELSE false END) AS reacted from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
                     left outer join bookmark_tb bt on ab.article_id = bt.article_id AND bt.user_id = #{userId}
                     left outer join react_tb rt on ab.article_id = rt.article_id AND rt.user_id = #{userId}
            where publish_date :: date = (current_date -1) AND ab.user_id = #{userId} AND isBan = false AND is_author = true  ORDER BY ab.publish_date desc limit #{pageNumber} offset #{nextPage}
                        """)
    List<ArticleResponse2> getAllArticleCurrentUserByYesterday(String userId, Integer pageNumber, Integer nextPage);

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
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count, (CASE WHEN bt.user_id = #{userId} THEN true ELSE false END) AS bookmarked, (CASE WHEN rt.status = true THEN true ELSE false END) AS reacted from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
                     left outer join bookmark_tb bt on ab.article_id = bt.article_id AND bt.user_id = #{userId}
                     left outer join react_tb rt on ab.article_id = rt.article_id AND rt.user_id = #{userId}
            where date_trunc('week', publish_date) = date_trunc('week', current_date) AND ab.user_id = #{userId} AND isBan = false AND is_author = true  ORDER BY ab.publish_date desc limit #{pageNumber} offset #{nextPage}
                        """)
    List<ArticleResponse2> getAllArticleCurrentUserPerWeek(String userId, Integer pageNumber, Integer nextPage);

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
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count, (CASE WHEN bt.user_id = #{userId} THEN true ELSE false END) AS bookmarked, (CASE WHEN rt.status = true THEN true ELSE false END) AS reacted from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
                     left outer join bookmark_tb bt on ab.article_id = bt.article_id AND bt.user_id = #{userId}
                     left outer join react_tb rt on ab.article_id = rt.article_id AND rt.user_id = #{userId}
            where EXTRACT(MONTH FROM publish_date) = EXTRACT(MONTH FROM current_date)
                    AND EXTRACT(YEAR FROM publish_date) = EXTRACT(YEAR FROM current_date) AND ab.user_id = #{userId} AND isBan = false AND is_author = true  ORDER BY ab.publish_date desc limit #{pageNumber} offset #{nextPage}
                        """)
    List<ArticleResponse2> getAllArticleCurrentUserPerMonth(String userId, Integer pageNumber, Integer nextPage);

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
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count, (CASE WHEN bt.user_id = #{userId} THEN true ELSE false END) AS bookmarked, (CASE WHEN rt.status = true THEN true ELSE false END) AS reacted from article_tb ab
                     inner join user_tb ub on ab.user_id = ub.user_id
                     inner join category c on c.category_id = ab.category_id
                     left outer join bookmark_tb bt on ab.article_id = bt.article_id AND bt.user_id = #{userId}
                     left outer join react_tb rt on ab.article_id = rt.article_id AND rt.user_id = #{userId}
            where date_trunc('year', publish_date) = date_trunc('year', current_date)
                    AND EXTRACT(YEAR FROM publish_date) = EXTRACT(YEAR FROM current_date) AND ab.user_id = #{userId} AND isBan = false AND is_author = true  ORDER BY ab.publish_date desc limit #{pageNumber} offset #{nextPage}
                        """)
    List<ArticleResponse2> getAllArticleCurrentUserPerYear(String userId, Integer pageNumber, Integer nextPage);


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




    @Select({
            """
            <script>
                SELECT article_id, title, sub_title
                FROM article_tb
                WHERE 1=1
                <if test='title != null'>
                    AND title LIKE CONCAT('%', #{title}, '%')
                </if>
                <if test='publishDate != null'>
                    AND publish_date = #{publishDate}
                </if>
                <if test='categoryId != null'>
                    AND category_id = #{categoryId}
                </if>
            </script>
            """
    })

    List<ArticleResponse2> filterArticles(@Param("title") String title, @Param("publishDate") Date publishDate, @Param("categoryId") String categoryId);


    @SelectProvider(type = ArticleMapperProvider.class, method = "getArticleByTitle")
    List<ArticleResponse2> getArticleByIdTest();



    @SelectProvider(type = ArticleMapperProvider.class, method = "filterArticles")
    List<ArticleResponse2> getArticlesByFilter(@Param("title") String title, @Param("publishDate") Date publishDate, @Param("categoryId") String categoryId);




    @SelectProvider(type = ArticleMapperProvider.class, method = "filterArticles2")
    List<ArticleResponse2> getArticlesByFilter2(FilterArticleCriteria filter);


    @Select("""
            SELECT count(article_id) as totalRecords from article_tb;
            """)
    Integer totalArticles();

    @Select("""
            
            """)
    Integer totalArticleForCurrentAuthor(String userId);

    @Select("""
            SELECT count(*) FROM article_tb WHERE user_id = #{userId}
            AND publish_date :: date = (current_date -1)
            """)
    Integer  totalArticleRecordByYesterdayForCurrentAuthor(String userId);

    @Select("""
            SELECT count(*) FROM article_tb WHERE user_id = #{userId}
            AND DATE_TRUNC('week', publish_date) = DATE_TRUNC('week', current_date)
            """)
    Integer  totalArticleRecordByPerWeekForCurrentAuthor(String userId);


    @Select("""
            SELECT count(*) FROM article_tb WHERE user_id = #{userId}
            AND EXTRACT(MONTH FROM publish_date) = EXTRACT(MONTH FROM current_date)
              AND EXTRACT(YEAR FROM publish_date) = EXTRACT(YEAR FROM current_date);
            """)
    Integer  totalArticleRecordByPerMonthForCurrentAuthor(String userId);


    @Select("""
            SELECT count(*) FROM article_tb WHERE user_id = #{userId}
            AND date_trunc('year', publish_date) = date_trunc('year', current_date)
            AND EXTRACT(YEAR FROM publish_date) = EXTRACT(YEAR FROM current_date) 
                        
           """)
    Integer  totalArticleRecordByPerYearForCurrentAuthor(String userId);




    @Select("""
            update article_tb set isban = true where article_id = #{articleId} returning isban
            """)
    boolean adminBanArticle(String articleId);



    @Select("""
            select exists(select 1 from article_tb where article_id = #{articleId} and article_tb.isBan = true)
            """)
    boolean validateIsArticleAlreadyBand(String articleId);

    @Select("""
select ab.article_id,
       ab.user_id,
       ab.category_id,
       ab.title,
       ab.sub_title,
       ab.publish_date,
       ab.description,
       ab.updatedat,
       coalesce((nullif(ab.image, '')),
                'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80') as image,
       ab.count_view,
       ab.isban,
       ab.hero_card_in,
       ub.photo_url,
       ub.username                                                                                                                                                                        as author_name,
       c.category_name,
       (select count(react_id) as react_count
        from react_tb
        where react_tb.article_id = ab.article_id)  
    FROM article_tb ab
    inner join user_tb ub on ab.user_id = ub.user_id
    inner join category c on c.category_id = ab.category_id
where ab.article_id = #{articleId}
            """)
    ArticleResponse getArticleByIdForBookmarkAndHistory(String articleId, String userId);



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
                   (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count, (CASE WHEN bt.user_id = #{userId} THEN true ELSE false END) AS bookmarked, (CASE WHEN rt.status = true THEN true ELSE false END) AS reacted from article_tb ab
                                                                                                                                                                                                                                                                   inner join user_tb ub on ab.user_id = ub.user_id
                                                                                                                                                                                                                                                                   inner join category c on c.category_id = ab.category_id
                                                                                                                                                                                                                                                                   left outer join bookmark_tb bt on ab.article_id = bt.article_id AND bt.user_id = #{userId}
                left outer join react_tb rt on ab.article_id = rt.article_id AND rt.user_id = #{userId}
            where ab.user_id = #{userId} AND isBan = false AND is_author = true ORDER BY ab.publish_date desc LIMIT 10 OFFSET 0
            """)
    List<ArticleResponse2> getAllArticlesByAuthorId(@Param("userId") String userId);

    
}