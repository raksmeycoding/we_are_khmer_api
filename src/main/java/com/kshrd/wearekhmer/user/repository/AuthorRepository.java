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

    @Select("""
            select * from insert_or_update_user_request_as_author(#{userId}, #{authorRequestName}, #{reason})
            """)
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
            WITH inserted_user_role AS (
                INSERT INTO user_role_tb (user_id, role_id)
                    VALUES (
                               (SELECT user_id FROM user_tb WHERE user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0'),
                               (SELECT role_id FROM role_tb WHERE name = 'ROLE_AUTHOR')
                           )
                    RETURNING user_id
            )
            UPDATE user_tb
            SET is_author = TRUE
            WHERE user_id = (SELECT user_id FROM inserted_user_role);
                        
            UPDATE author_request_tb
            SET is_author_accepted = TRUE
            WHERE author_request_tb.user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0'
            RETURNING user_id;
            """)
    String updateUserRequestToBeAsAuthor(String userId);


    @Select("""
            select update_tables_author_request_tb_and_user_tb(false, #{userId})
            """)
    boolean updateUserRequestToBeAsAuthorAsReject(String userId);


}



