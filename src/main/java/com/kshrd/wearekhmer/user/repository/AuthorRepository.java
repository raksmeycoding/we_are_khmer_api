package com.kshrd.wearekhmer.user.repository;


import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.AuthorRequestTable;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Mapper
public interface AuthorRepository {

    @Select("SELECT * FROM author_request_tb")
    @Results(id = "authorRequestMap", value = {
            @Result(property = "authorRequestId", column = "author_request_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "authorRequestName", column = "author_request_name"),
            @Result(property = "isAuthorAccepted", column = "is_author_accepted"),
            @Result(property = "createdAt", column = "createat"),
            @Result(property = "reason", column = "reason")
    })
    List<AuthorRequestTable> getAll();

    @Select("SELECT * FROM author_request_tb WHERE user_id = #{userId}")
    @ResultMap("authorRequestMap")
    List<AuthorRequestTable> getAllByUserId(String userId);

    @Select("SELECT * FROM author_request_tb WHERE author_request_id = #{authorRequestId}")
    @ResultMap("authorRequestMap")
    AuthorRequestTable getById(String authorRequestId);

    @Select("INSERT INTO author_request_tb (user_id, author_request_name, reason) " +
            "VALUES (#{userId}, #{authorRequestName}, #{reason}) returning *")
    @ResultMap("authorRequestMap")
    AuthorRequestTable insert(AuthorRequestTable authorRequest);

    @Select("UPDATE author_request_tb SET user_id = #{userId}, author_request_name = #{authorRequestName}, " +
            "is_author_accepted = #{isAuthorAccepted}, reason = #{reason} WHERE author_request_id = #{authorRequestId} returning *")
    @ResultMap("authorRequestMap")
    AuthorRequestTable update(AuthorRequestTable authorRequest);

    @Select("DELETE FROM author_request_tb WHERE author_request_id = #{authorRequestId} returning *")
    AuthorRequestTable delete(String authorRequestId);


    @Select("""
            select * from user_tb where is_author = true
            """)

    @Results(id = "authorDTO",
            value = {
                    @Result(property = "authorId", column = "user_id"),
                    @Result(property = "authorName", column = "username"),
                    @Result(property = "photoUrl", column = "photo_url"),
                    @Result(property = "dateOfBirth", column = "data_of_birth"),
                    @Result(property = "gender", column = "gender"),
                    @Result(property = "workingExperience", column = "user_id", many = @Many(select = "com.kshrd.wearekhmer.user.repository.WorkingExperienceMapper.getByUserId")),
                    @Result(property = "education", column = "user_id", many = @Many(select = "com.kshrd.wearekhmer.user.repository.EducationMapper.getEducationByUserIdObject")),
                    @Result(property = "quote", column = "user_id", many = @Many(select = "com.kshrd.wearekhmer.user.repository.QuoteMapper.getQuoteByUserIdAsObject"))
            })
    List<AuthorDTO> getAllAuthor();



    @Select("""
            select update_tables_author_request_tb_and_user_tb(true, #{userId})
            """)
    boolean updateUserRequestToBeAsAuthor(String userId);


    @Select("""
            select update_tables_author_request_tb_and_user_tb(false, #{userId})
            """)
    boolean updateUserRequestToBeAsAuthorAsReject(String userId);


}



