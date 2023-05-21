package com.kshrd.wearekhmer.userArtivities.repository;

import com.kshrd.wearekhmer.userArtivities.model.UserComment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ICommentRepository {

    @Select("""
            select cb.*,
                   (select user_tb.username  from comment_tb inner join user_tb on comment_tb.user_id = user_tb.user_id where comment_tb.parent_id is not null and comment_tb.parent_id = cb.comment_id ) as author_replay_name,
                   (select comment from comment_tb where comment_tb.parent_id is not null and comment_tb.parent_id = cb.comment_id) as author_replay_comment
            from comment_tb cb where cb.parent_id is null and article_id = #{articleId}
            """)
    @Result(property = "author_replay_name", column = "author_replay_name")
    List<UserComment> getUserCommentByArticleId(String articleId);


    @Select("""
            INSERT INTO comment_tb (user_id, article_id, comment)
            VALUES (#{user_id}, #{article_id}, #{comment}) returning *
            """)
    @Result(property = "author_replay_name", column = "author_replay_name")
    UserComment creatArticleComment(String user_id, String article_id, String comment);

}
