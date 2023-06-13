package com.kshrd.wearekhmer.userRating;


import com.kshrd.wearekhmer.userRating.dto.RatingDto;
import com.kshrd.wearekhmer.userRating.reponse.PersonalInformationResponse;
import com.kshrd.wearekhmer.userRating.reponse.RatingBarResponse;
import com.kshrd.wearekhmer.userRating.reponse.RatingResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
            select round(sum(number_of_rating)::numeric / count(*)::numeric, 2) as rating_average,
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


    @Select("""
            select case number_of_rating
                       when 1 then 'Poor'
                       when 2 then 'Below_Average'
                       when 3 then 'Average'
                       when 4 then 'Good'
                       when 5 then 'Excellence'
                       else 'Unknown'
                       end  as rating_name,
                   number_of_rating as rating_number,
                   count(*) as rating_count
            from rating_tb
            where author_id = #{authorId}
            group by number_of_rating;
            """)
    List<RatingBarResponse> getRatingBarByAuthorId(String authorId);


    @Select("""
            select sum(count_view) as total_view from article_tb a where user_id = #{authorId}
            """)
    Integer getTotalViewAllRecordsByAuthorId(String authorId);
//    'd0e1bd19-d4bc-45d5-9beb-32538d16b769'




}
