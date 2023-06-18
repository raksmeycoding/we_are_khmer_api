package com.kshrd.wearekhmer.repository;


import com.kshrd.wearekhmer.user.model.dto.UserAppDTO;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;


public interface WeAreKhmerRepositorySupport {
    @Select("""
            select * from user_tb where email = #{email}
            """)
    @Results(
            id = "userMapper",
            value = {
                    @Result(property = "userId", column = "user_id"),
                    @Result(property = "userName", column = "username"),
                    @Result(property = "email", column = "email"),
                    @Result(property = "password", column = "password"),
                    @Result(property = "photoUrl", column = "photo_url"),
                    @Result(property = "dataOfBirth", column = "data_of_birth"),
                    @Result(property = "isEnable", column = "is_enable"),
                    @Result(property = "isAuthor", column = "is_author"),
                    @Result(property = "gender", column = "gender"),
                    @Result(property = "roles", column = "user_id",
                            many = @Many(select = "getUserRolesById"))
            }
    )
    UserApp findUserByEmail(@Param("email") String email);


    @Select("""
            select rt.name as role_name from user_tb utb inner join user_role_tb urt on utb.user_id = urt.user_id
                                          inner join role_tb rt on urt.role_id = rt.role_id
                                          where urt.user_id = #{userId}
            """)
    List<String> getUserRolesById(@Param("userId") String userId);

    @Select("""
            select rt.name as role_name from user_tb utb inner join user_role_tb urt on utb.user_id = urt.user_id
            inner join role_tb rt on urt.role_id = rt.role_id
            where email = #{email}
            """)
    List<String> getUserRolesByUserEmail(@Param("email") String email);


    @Select("""
            insert into user_tb (username, email, password, photo_url, gender) values (#{para.username}, #{para.email}, #{para.password}, #{para.photo_url} , cast(#{para.gender} as gender)) returning * 
            """)
    @ResultMap("userMapper")
    <P> UserApp normalUserRegister(@Param("para") P p);


    //    insert role to normal user just register as author
    @Select("""
            insert into user_role_tb (user_id, role_id) values ((select user_id from user_tb where user_tb.user_id = #{userId}),(select role_id from role_tb where name = 'ROLE_AUTHOR')) returning user_id
            """)
    String registerAsAuthorAndReturnUserId(@Param("userId") String userId);

//    get userApp my userId


    @Select("""
            select * from user_tb where user_id = #{userId}
            """)
    @ResultMap("userMapper")
    UserApp getUserAppById(@Param("userId") String userId);

    @Select("""
            select * from user_tb where user_id = #{userId}
            """)
    @ResultMap("userMapper")
    UserAppDTO getUserAppDTOById(@Param("userId") String userId);

    default UserApp registerAsAuthorAndReturnUserApp(String userId) {
        String userId1 = registerAsAuthorAndReturnUserId(userId);
        return getUserAppById(userId1);
    }


    @Select("""
            select * from upload_image_to_specific_table_User_tb(#{imageName}, #{userId});
            """)
    String uploadImageToUserTb(String imageName, String userId);

    @Select("""
            select * from upload_image_to_specific_table_article_tb (#{imageName} ,#{articleId})
            """)
    String uploadImageToArticleTb(String imageName, String articleId);


    @Select("""
            select * from upload_image_to_specific_table_category_tb (#{imageName} ,#{categoryId})
            """)
    String uploadImageToCategoryTb(String imageName, String categoryId);


    @Select("""
            select utb.user_id, utb.username, utb.email, rt.name as role_name from user_tb utb inner join user_role_tb urt on utb.user_id = urt.user_id
            inner join role_tb rt on urt.role_id = rt.role_id where email = #{email}
            """)
    @Results(
            id = "userMapper2",
            value = {
                    @Result(property = "userId", column = "user_id"),
                    @Result(property = "userName", column = "username"),
                    @Result(property = "email", column = "email"),
                    @Result(property = "password", column = "password"),
                    @Result(property = "photoUrl", column = "photo_url"),
                    @Result(property = "dataOfBirth", column = "data_of_birth"),
                    @Result(property = "isEnable", column = "is_enable"),
                    @Result(property = "gender", column = "gender"),
//                    @Result(property = "roles", column = "role_name",
//                    one = @One(select = "getUserRolesByUserEmail"))
            }
    )
    UserApp getAllUsers(@Param("email") String email);





    @Select("""
            update user_tb set gender = cast(#{gender} as gender), username = #{username} where user_id = #{userId}
            """)
    void changeGenderAfterUserRequestAsAuthorSuccess( String userId, String gender, String username);


//    @Select("""
//            update user_tb set data_of_birth = cast(#{dateOfBirth} as timestamp) where user_id = ${userId}
//            """)
//    void updateDateOfBirtOfUserAfterRegistedAsAuthor(String userId, Timestamp dateOfBirth);



    @Update("UPDATE user_tb SET data_of_birth = #{dateOfBirth} WHERE user_id = #{userId}")
    void updateDateOfBirthOfUserAfterRegisteredAsAuthor(@Param("userId") String userId, @Param("dateOfBirth") Timestamp dateOfBirth);



}
