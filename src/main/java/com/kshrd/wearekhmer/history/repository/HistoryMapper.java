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


    @Select("""
            DELETE FROM history_tb WHERE history_id = #{historyId} returning *
            """)
    @ResultMap("historyResultMap")
    History deleteHistory(History history);



    @Select("""
            SELECT * FROM history_tb WHERE user_id = #{userId}
            """)
    @Result(property = "historyId", column = "history_id")
    @Result(property = "userId", column = "user_id")
    @Result(property = "createdAt", column = "created_at")
    @Result(property = "article", column = "article_id", many = @Many(select = "com.kshrd.wearekhmer.article.repository.ArticleMapper.getArticleById"))
    List<HistoryResponse> getAllHistoryByCurrentUser(String userId);






}
