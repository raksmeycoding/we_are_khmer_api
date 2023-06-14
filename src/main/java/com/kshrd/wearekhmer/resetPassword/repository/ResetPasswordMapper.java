package com.kshrd.wearekhmer.resetPassword.repository;


import com.kshrd.wearekhmer.resetPassword.model.entity.Reset;
import com.kshrd.wearekhmer.resetPassword.model.request.InputEmail;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ResetPasswordMapper {

    @Select("""
            SELECT EXISTS(SELECT 1 FROM user_tb Where email = #{email})
            """)
    boolean checkEmailExist(String email);


    @Select("""
                SELECT  * from user_tb WHERE email = #{email}
            """)
    UserApp getInformation(InputEmail email);


    @Select("""
            SELECT EXISTS(SELECT 1 FROM reset_password_tb WHERE email = #{email});
            """)
    boolean checkEmailExistInResetTb(String email);

    @Select("""
            INSERT INTO reset_password_tb VALUES (DEFAULT,#{token}, #{email}, DEFAULT) returning *
            """)
    @Results(
            id = "resetPassword",
            value = {
                    @Result(property = "resetPasswordId", column = "reset_password_id"),
                    @Result(property = "token", column = "token"),
                    @Result(property = "email", column = "email"),
                    @Result(property = "isVerified", column = "is_verified"),
            }
    )
    Reset createVerification(Reset reset);


    @Select("""
            SELECT EXISTS(SELECT 1 FROM reset_password_tb WHERE token = #{token})
            """)
    boolean verifyToken(String token);


    @Select("""
            UPDATE reset_password_tb 
            SET is_verified = true
            WHERE token = #{token}
            """)
    @ResultMap("resetPassword")
    Reset tokenVerifySuccess(String token);

    @Select("""
            UPDATE user_tb 
            SET password = #{newPassword}
            WHERE email = #{email}
            """)
    Reset resetPassword(String email, String newPassword);

    @Select("""
            SELECT EXISTS(SELECT 1 FROM reset_password_tb WHERE email = #{email} AND is_verified = true)
            """)
    boolean checkEmailAlreadyVerified(String email);

    @Select("""
            DELETE FROM reset_password_tb WHERE email = #{email} AND is_verified = true
            """)
    Reset removeToken(String email);




}
