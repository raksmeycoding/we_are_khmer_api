package com.kshrd.wearekhmer.user.repository;


import com.kshrd.wearekhmer.repository.WeAreKhmerRepositorySupport;
import com.kshrd.wearekhmer.user.model.dto.UserAppDTO;
import com.kshrd.wearekhmer.user.model.entity.Users;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserAppRepository extends WeAreKhmerRepositorySupport {



    @Select("""
            select * from user_tb where is_enable = true;
            """)
    @ResultMap("userMapper")
    List<UserApp> getAllUser();

    @Select("""
            select * from user_tb where is_author = true
            """)
    @ResultMap("userMapper")
    List<UserApp> getAllUserByStatusIsAuthor();


    @Select("""
            select * from user_tb where is_author = true and user_id = #{authorId}
            """)
    @ResultMap("userMapper")
    UserApp getByStatusIsAuthorById(String authorId);


    @Select("""
            select exists(select 1 from user_tb where user_tb.user_id = #{userId})
            """)
    boolean userExist(String userId);

    @Select("""
            SELECT user_id, username, email, photo_url, data_of_birth, gender FROM user_tb WHERE is_author = #{isAuthor} AND is_enable = true
             LIMIT #{pageNumber} OFFSET #{nextPage};
            """)
    @Result(property = "userId", column = "user_id")
    @Result(property = "userName", column = "username")
    @Result(property = "email", column = "email")
    @Result(property = "photoUrl", column = "photo_url")
    @Result(property = "dateOfBirth", column = "data_of_birth")
    @Result(property = "gender", column = "gender")
   List<Users>  dynamicUserAndAuthor(boolean isAuthor, Integer pageNumber, Integer nextPage);

    @Select("""
            SELECT count(user_id) FROM user_tb WHERE is_author = #{isAuthor} AND is_enable = true;
            """)
    Integer countUserOrAuthor(boolean isAuthor);



}
