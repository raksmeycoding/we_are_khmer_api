package com.kshrd.wearekhmer.history.repository;

import com.kshrd.wearekhmer.article.model.entity.Article;
import com.kshrd.wearekhmer.history.model.entity.History;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HistoryMapper {
    @Results(id = "historyResultMap", value = {
            @Result(property = "historyId", column = "history_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "articleId", column = "article_id"),
            @Result(property = "createdAt", column = "created_at"),
    })
    @Select("INSERT INTO history_tb (user_id, article_id) " +
            "VALUES (#{userId}, #{articleId}) returning *")
    History insertHistory(History history);

//    @Select("""
//            SELECT * FROM history_tb WHERE user_id = #{userId}
//            """)
//    @ResultMap("historyResultMap")
//    List<History> getAllHistoryByCurrentUser(History history);


    @Select("""
            DELETE FROM history_tb WHERE history_id = #{historyId} returning *
            """)
    @ResultMap("historyResultMap")
    History deleteHistory(History history);



    @Select("""
            SELECT * FROM history_tb
            WHERE user_id = #{userId}
            ORDER BY created_at DESC
            ;
            """)
    @Result(property = "historyId", column = "history_id")
    @Result(property = "userId", column = "user_id")
    @Result(property = "createdAt", column = "created_at")
    @Result(property = "article", column = "article_id", many = @Many(select = "com.kshrd.wearekhmer.article.repository.ArticleMapper.getArticleById"))
    List<HistoryResponse> getAllHistoryByCurrentUser(String userId);

    @Select("""
            DELETE FROM history_tb WHERE user_id = #{userId} returning *
            """)
    @ResultMap("historyResultMap")
    List<History> removeAllHistory(History history);


    @Select("""
            UPDATE history_tb
            SET created_at = current_timestamp
            WHERE article_id = #{articleId} AND user_id = #{userId} returning *
            """)
    @ResultMap("historyResultMap")
    History updateHistory(String articleId, String userId);

    @Select("""
            SELECT EXISTS(SELECT 1 FROM history_tb WHERE article_id = #{articleId} AND user_id = #{userId})
            """)
    Boolean getAllHistoryCurrentId(String articleId, String userId);


    @Select("""
            SELECT EXISTS(SELECT 1 FROM history_tb WHERE history_id = #{historyId} AND user_id = #{userId})
            """)
    Boolean validateHistoryId(String historyId, String userId);





//    @Select("""
//            SELECT * FROM history_tb
//            WHERE user_id = #{userId}
//            ORDER BY created_at DESC
//            ;
//            """)
//    HistoryResponse validateHistoryRemoveAll(History history);
}
