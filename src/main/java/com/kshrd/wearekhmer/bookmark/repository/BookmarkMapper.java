package com.kshrd.wearekhmer.bookmark.repository;


import com.kshrd.wearekhmer.bookmark.model.entity.Bookmark;
import com.kshrd.wearekhmer.bookmark.model.reponse.BookmarkResponse;
import com.kshrd.wearekhmer.history.model.entity.History;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;
import org.apache.ibatis.annotations.*;

import java.awt.print.Book;
import java.util.List;

@Mapper
public interface BookmarkMapper {
    @Results(id = "bookmarkResultMap", value = {
            @Result(property = "bookmarkId", column = "bookmark_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "articleId", column = "article_id"),
            @Result(property = "createdAt", column = "created_at"),
    })
    @Select("INSERT INTO bookmark_tb (user_id, article_id) " +
            "VALUES (#{userId}, #{articleId}) returning *")
    Bookmark insertBookmark(Bookmark bookmark);


    @Select("""
            DELETE FROM bookmark_tb WHERE bookmark_id = #{bookmarkId} returning *
            """)
    @ResultMap("bookmarkResultMap")
    Bookmark deleteBookmark(Bookmark bookmark);

    @Select("""
            SELECT * FROM bookmark_tb WHERE user_id = #{userId} ORDER BY created_at DESC
            """)
    @Result(property = "bookmarkId", column = "bookmark_id")
    @Result(property = "userId", column = "user_id")
    @Result(property = "createdAt", column = "created_at")
    @Result(property = "article", column = "article_id", many = @Many(select = "com.kshrd.wearekhmer.article.repository.ArticleMapper.getArticleById"))
    List<BookmarkResponse> getAllBookmarkByCurrentId(String userId);


    @Select("""
            DELETE FROM bookmark_tb WHERE user_id = #{userId} returning *
            """)
    @ResultMap("bookmarkResultMap")
    List<Bookmark> removeAllBookmark(Bookmark bookmark);

    @Select("""
            SELECT EXISTS(SELECT 1 FROM bookmark_tb WHERE article_id = #{articleId} AND user_id = #{userId})
            """)
    Boolean getAllBookmarkCurrentId(String articleId, String userId);

    @Select("""
            DELETE FROM bookmark_tb WHERE article_id = #{articleId} AND user_id = #{userId}
            """)
    Bookmark deleteBookmarkByArticleId(String articleId, String userId);
}
