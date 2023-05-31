package com.kshrd.wearekhmer.user.repository;


import com.kshrd.wearekhmer.repository.WeAreKhmerRepositorySupport;
import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.UserApp;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserAppRepository extends WeAreKhmerRepositorySupport {

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



}
