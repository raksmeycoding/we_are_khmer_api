package com.kshrd.wearekhmer.userReviewAuthor.repository;


import com.kshrd.wearekhmer.userReviewAuthor.model.entity.UserReviewAuthor;
import com.kshrd.wearekhmer.userReviewAuthor.model.response.UserReviewAuthorResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.UUID;

@Mapper
public interface UserReviewAuthorMapper {


    @Select("""
            insert into user_review_author_tb (user_id, author_id, author_name, comment)
            values (#{user_id}, (select user_tb.user_id from user_tb where is_author = 'true' and user_tb.user_id = #{author_id}),
                    (select username from user_tb where is_author = true and user_tb.user_id = #{author_id}), #{comment}) returning *
            """)
    UserReviewAuthor insertUserReviewAuthorByCurrentUserId(UserReviewAuthor userReviewAuthor);


    @Select("""
                SELECT urat.author_id, urat.user_review_author_id,urat.user_id,author_name,
                       create_at,update_at, urat.comment, ut.username, ut.photo_url
                FROM user_review_author_tb urat INNER JOIN user_tb ut on urat.user_id = ut.user_id
                WHERE author_id = #{authorId} ORDER BY create_at DESC LIMIT 4 OFFSET 0
            """)
    @Result(property = "photoUrl", column = "photo_url")
    @Result(property = "senderName", column = "username")
    List<UserReviewAuthorResponse> getAllUserReviewAuthorByAuthorId(String authorId);


}
