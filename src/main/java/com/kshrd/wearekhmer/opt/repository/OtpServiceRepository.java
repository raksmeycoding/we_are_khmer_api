package com.kshrd.wearekhmer.opt.repository;


import com.kshrd.wearekhmer.opt.model.Otp;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OtpServiceRepository {


    @Select("""
            insert into otp2(token, email, temp_password, expiredAt, user_id)
            values (#{token},#{email}, #{temp_password}, #{expiredAt}, #{userId})
            returning *
            """)

    @Results(
            id = "optMap",
            value = {
                    @Result(property = "optId", column = "token_id"),
                    @Result(property = "token", column = "token"),
                    @Result(property = "email", column = "email"),
                    @Result(property = "temp_password", column = "temp_password"),
                    @Result(property = "createAt", column = "createat"),
                    @Result(property = "expiredAt", column = "expiredat"),
                    @Result(property = "isexpired", column = "isExpired"),
                    @Result(property = "userId", column = "user_id")
            }
    )
    Otp createVerificationToken(Otp otp);


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
            WITH updated_users AS (
                UPDATE user_tb
                    SET is_enable = true
                    WHERE user_id = (SELECT user_id FROM otp2 WHERE otp2.token = #{token})
                    RETURNING *
            )
            SELECT otp2.*
            FROM otp2
                     JOIN updated_users ON otp2.user_id = updated_users.user_id;
            """)
    @Result(property = "optId", column = "token_id")
    @Result(property = "token", column = "token")
    @Result(property = "email", column = "email")
    @Result(property = "temp_password", column = "temp_password")
    @Result(property = "createAt", column = "createat")
    @Result(property = "expiredAt", column = "expiredat")
    @Result(property = "isexpired", column = "isExpired")
    @Result(property = "userId", column = "user_id")
    Otp enableUserByToken(String token);


}
