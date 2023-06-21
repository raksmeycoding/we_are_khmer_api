package com.kshrd.wearekhmer.userArtivities.repository;

import com.kshrd.wearekhmer.userArtivities.model.AuthorReplyCommentMapperResponse;
import com.kshrd.wearekhmer.userArtivities.model.UserComment;
import com.kshrd.wearekhmer.userArtivities.model.dto.AuthorReplyCommentMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ICommentRepository {

    @Select("""
            select cb.*, ut.photo_url, ut.username
            from comment_tb cb inner join article_tb a on a.article_id = cb.article_id inner join user_tb ut on cb.user_id = ut.user_id
            where cb.parent_id is null
              and cb.article_id = #{articleId}
            """)
    @Result(property = "comment_id", column = "comment_id")
    @Result(property = "author_reply", column = "comment_id", many = @Many(select = "getAuthorReplyCommentByCommentId"))
    List<UserComment> getUserCommentByArticleId(String articleId);


    @Select("""
            INSERT INTO comment_tb (user_id, article_id, comment)
            VALUES (#{user_id}, #{article_id}, #{comment}) returning *
            """)
    @Result(property = "author_replay_name", column = "author_replay_name")
    UserComment creatArticleComment(String user_id, String article_id, String comment);


    @Select("""
            INSERT INTO comment_tb (user_id, article_id, parent_id, comment)
            values ((select a.user_id
                     from article_tb a
                     where a.article_id =
                           (select c.article_id from comment_tb c where c.comment_id = #{comment_id})),
                    (select c.article_id from comment_tb c where c.comment_id = #{comment_id}),
                    #{comment_id}, #{comment}) returning *;
            """)
    UserComment authorReplyCommentToHisArticle(AuthorReplyCommentMapper authorReplyCommentMapper);


    @Select("""
            select exists(select 1 from comment_tb where parent_id = #{parentId})
            """)
    boolean validateParentIdExist(String parentId);


    @Select("""
            select c.*, u.username, u.photo_url from comment_tb c inner join article_tb a on a.article_id = c.article_id inner join user_tb u on a.user_id = u.user_id where parent_id = #{comment_id}
            """)
    AuthorReplyCommentMapperResponse getAuthorReplyCommentByCommentId(@Param("comment_id") String comment_id);




    @Select("""
            select exists(select a.user_id  from comment_tb c inner join article_tb a on a.article_id = c.article_id where comment_id = #{commentId} and a.user_id = #{userId});
            """)
    boolean validateAuthorHasAuthorityToReplyComment(String commentId, String userId);

    @Select("""
            SELECT COUNT(*) FROM comment_tb WHERE article_id = #{articleId}
            """)
    Integer countComment(String articleId);


}
