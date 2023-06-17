package com.kshrd.wearekhmer.opt.repository;


import com.kshrd.wearekhmer.opt.model.Otp;
import com.kshrd.wearekhmer.opt.model.Otp2;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OtpServiceRepository {


    @Select("""
            insert into otp_tb(token, email, temp_password, expiredAt, user_id)
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
            select * from otp_tb where token = #{token}
            """)
    @ResultMap("optMap")
    Otp findByToken(@Param("token") String token);


    @Select("""
            delete from otp_tb where token = #{token} returning * 
             """)
    @ResultMap("optMap")
    Otp removeByToken(@Param("token") String token);


    @Select("""
            WITH updated_users AS (
                UPDATE user_tb
                    SET is_enable = true
                    WHERE user_id = (SELECT user_id FROM otp_tb WHERE otp_tb.token = #{token})
                    RETURNING *
            )
            SELECT otp_tb.*
            FROM otp_tb
                     JOIN updated_users ON otp_tb.user_id = updated_users.user_id;
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


    @Select("""
            select *
            from handle_resend_otp_tb(#{email});
            """)
    Otp2 resendVerificationTokenToVerifyEmail(String email);

    @Select("""
            select * from validUserInputPassword(#{userEmail});
            """)
    String getUserRawPasswordByUserEmail(String userEmail);


}
