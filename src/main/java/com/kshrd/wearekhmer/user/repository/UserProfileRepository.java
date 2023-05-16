package com.kshrd.wearekhmer.user.repository;

import com.kshrd.wearekhmer.user.model.entity.AuthorProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserProfileRepository {


    @Select("""
            select * from profile_tb
            """)
    @Results(
            id = "authorProfileMap",
            value = {
                    @Result(property = "userId", column = "user_id"),
                    @Result(property = "workingExperience", column = "working_experience"),
                    @Result(property = "education", column = "education"),
                    @Result(property = "quote", column = "quote")
            }
    )
    List<AuthorProfile> getAllAuthorProfile();

}
