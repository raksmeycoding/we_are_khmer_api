package com.kshrd.wearekhmer.userReviewAuthor.repository;


import com.kshrd.wearekhmer.userReviewAuthor.model.entity.UserReviewAuthor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserReviewAuthorMapper {


    @Select("""
            insert into user_review_author_tb (user_id, author_id, author_name, comment)
            values (#{user_id}, (select user_tb.user_id from user_tb where is_author = 'true' and user_tb.user_id = #{author_id}),
                    (select username from user_tb where is_author = true and user_tb.user_id = #{author_id}), #{comment}) returning *
            """)
    UserReviewAuthor insertUserReviewAuthorByCurrentUserId(UserReviewAuthor userReviewAuthor);


    @Select("""
            select * from user_review_author_tb where author_id = #{authorId}
            """)
    List<UserReviewAuthor> getAllUserReviewAuthorByAuthorId(String authorId);
}
