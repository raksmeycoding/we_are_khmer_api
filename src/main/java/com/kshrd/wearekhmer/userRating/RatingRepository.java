package com.kshrd.wearekhmer.userRating;


import com.kshrd.wearekhmer.userRating.dto.RatingDto;
import com.kshrd.wearekhmer.userRating.reponse.RatingResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RatingRepository {


    @Select("""
            insert into rating_tb (user_id, author_id, number_of_rating)
            VALUES (#{ratingDto.user_id}, (select user_tb.user_id
                                                             from user_tb
                                                             where user_id = #{ratingDto.author_id}
                                                               and is_author = true), #{ratingDto.number_of_rating}) returning *
            """)
    Rating createUserRatingToAuthor(@Param("ratingDto") RatingDto ratingDto);

    @Select("""
            select sum(number_of_rating) / count(*) as sum_rating_number,
                   ub.username,
                   ub.photo_url
            from rating_tb rb
                     inner join user_tb ub on rb.author_id = ub.user_id
            where rb.author_id = #{authorId}
            group by ub.photo_url, ub.username;
            """)
    RatingResponse getRatingByAuthorId(String authorId);

    @Select("""
            select exists(select 1 from user_tb where user_id = #{authorId} and is_author = true );
            """)
    boolean isExistAuthor(String authorId);

}
