package com.kshrd.wearekhmer.user.repository;

import com.kshrd.wearekhmer.user.model.entity.Quote;
import com.kshrd.wearekhmer.user.model.entity.QuoteResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface QuoteMapper {

    @Select("SELECT * FROM quote_tb")
    @Results(
            id = "quoteMapperId",
            value = {

                    @Result(property = "quoteId", column = "q_id"),
                    @Result(property = "quoteName", column = "q_name"),
            }
    )
    List<QuoteResponse> getAll();

    @Select("SELECT * FROM quote_tb WHERE q_id = #{quoteId}")
    @ResultMap("quoteMapperId")
    Quote getById(String quoteId);



    @Select("""
            select qb.q_name as q_name from quote_tb qb where qb.user_id = #{userId}
            """)
    String getQuoteByUserId(String userId);

    @Select("""
            select * from quote_tb where user_id = #{userId}
            """)
    @ResultMap("quoteMapperId")
    QuoteResponse getQuoteByUserIdAsObject(String userId);

    @Select("INSERT INTO quote_tb(q_name, user_id) VALUES (#{quoteName}, #{userId}) returning *")
    @ResultMap("quoteMapperId")
    Quote insert(Quote quote);

    @Select("UPDATE quote_tb SET q_name = #{quoteName} WHERE q_id = #{quoteId} returning *")
    @ResultMap("quoteMapperId")
    Quote update(Quote quote);

    @Select("DELETE FROM quote_tb WHERE q_id = #{quoteId}")
    @ResultMap("quoteMapperId")
    Quote delete(String quoteId);


//    validate when user register again and again
    @Delete("""
            delete from quote_tb where user_id = #{useId}
            """)
    void deleteAllQuoteIfUserIdIsExist(String useId);
}

