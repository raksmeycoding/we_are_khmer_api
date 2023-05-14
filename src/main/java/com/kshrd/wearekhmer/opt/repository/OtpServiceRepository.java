package com.kshrd.wearekhmer.opt.repository;


import com.kshrd.wearekhmer.opt.model.Otp;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;

@Mapper
public interface OtpServiceRepository {


    @Select("""
            insert into otp2(token, expiredAt, user_id)
            values (#{token}, #{expiredAt}, #{userId}) returning *
            """)

    @Results(
            id = "optMap",
            value = {
                    @Result(property = "optId", column = "token_id"),
                    @Result(property = "token", column = "token"),
                    @Result(property = "createAt", column = "createat"),
                    @Result(property = "expiredAt", column = "expiredat"),
                    @Result(property = "isexpired", column = "isExpired"),
                    @Result(property = "userId", column = "user_id")
            }
    )
    Otp createVerificationToken(@Param("token") String token, @Param("expiredAt") Timestamp expiredAt, @Param("userId") String userId );


    @Select("""
            select * from otp2 where token = #{token}
            """)
    @ResultMap("optMap")
    Otp findByToken(@Param("token") String token);



    @Select("""
           delete from otp2 where token = #{token} returning * 
            """)
    @ResultMap("optMap")
    Otp removeByToken(@Param("token") String token);



    @Select("""
            update user_tb set is_enable = true where user_id = (select user_id from otp2 where otp2.token = #{token}) returning *
            """)
    @ResultMap("optMap")
    Otp enableUserByToken(@Param("token") String token);




}
