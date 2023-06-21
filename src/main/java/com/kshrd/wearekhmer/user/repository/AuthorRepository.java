package com.kshrd.wearekhmer.user.repository;


import com.kshrd.wearekhmer.user.model.dto.AuthorDTO;
import com.kshrd.wearekhmer.user.model.entity.*;
import com.kshrd.wearekhmer.userRating.reponse.PersonalInformationResponse;
import com.sun.mail.imap.protocol.BODY;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.TypeHandler;
import org.intellij.lang.annotations.JdkConstants;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
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
            select * from user_tb where is_author = true and user_id = #{authorId}
            """)

    @ResultMap("authorDTO")
    AuthorDTO getAllAuthorById(String authorId);




    @Select("""
            WITH inserted_user_role AS (
                INSERT INTO user_role_tb (user_id, role_id)
                    VALUES (
                               (SELECT user_id FROM user_tb WHERE user_id = #{userId}),
                               (SELECT role_id FROM role_tb WHERE name = 'ROLE_AUTHOR')
                           )
                    RETURNING user_id
            )
            UPDATE user_tb
            SET is_author = 'true'
            WHERE user_id = (SELECT user_id FROM inserted_user_role);
                        
            UPDATE author_request_tb
            SET is_author_accepted = 'APPROVED'
            WHERE author_request_tb.user_id = #{userId}
            RETURNING user_id;
            """)
    String updateUserRequestToBeAsAuthor(String userId);


    @Select("""
            update author_request_tb set is_author_accepted = 'REJECTED' WHERE  user_id = #{userId} RETURNING user_id;
            """)
    String updateUserRequestToBeAsAuthorAsReject(String userId);



    @Select("""
            select rt.name from user_tb inner join user_role_tb urt on user_tb.user_id = urt.user_id inner join role_tb rt on urt.role_id = rt.role_id where urt.user_id = #{userId} and rt.name = 'ROLE_AUTHOR'
            """)
    String userAlreadyAuthor(String userId);

    @Select("""
            select * from user_tb where is_author = true and user_id = #{authorId}
            """)
    @ResultMap("authorDTO")
    AuthorDTO getCurrentAuthorById(String authorId);




    @Select("""
            select u.user_id, u.username, u.email, u.gender, u.data_of_birth from user_tb u inner join user_role_tb urb on urb.user_id = u.user_id inner join  role_tb r on urb.role_id = r.role_id where u.user_id = #{authorId} and r.name = 'ROLE_AUTHOR'
            """)
    PersonalInformationResponse getAuthorPersonalInfoByAuthorId(String authorId);



    @Select("""
            select exists(select  1 from author_request_tb arb where user_id = #{authorId} and is_author_accepted = 'REJECTED')
            """)
    boolean checkAuthorRequestHadBendedOrRejected(String authorId);


    @Select("""
            select * from user_tb where is_author = true AND user_id = #{userId}
            """)
    @Result(property = "authorId", column = "user_id")
    @Result(property = "email", column = "email")
    @Result(property = "username", column = "username")
    @Result(property = "profile", column = "photo_url")
    @Result(property = "date", column = "data_of_birth")
    @Result(property = "gender", column = "gender")
    @Result(property = "workingExperience", column = "user_id", many = @Many(select = "com.kshrd.wearekhmer.user.repository.WorkingExperienceMapper.getByUserId"))
    @Result(property = "education", column = "user_id", many = @Many(select = "com.kshrd.wearekhmer.user.repository.EducationMapper.getEducationByUserIdObject"))
    @Result(property = "bio", column = "user_id", many = @Many(select = "com.kshrd.wearekhmer.user.repository.QuoteMapper.getQuoteByUserIdAsObject"))
    AccountSettingResponse getAccountSetting(@Param("userId") String userId);



    @Select("""
            UPDATE user_tb
            SET username = #{username},
                data_of_birth = #{dateOfBirth},
                gender = cast(#{gender} as gender)
                WHERE user_id = #{userId}
            """)
    UpdateAccountSetting updateAuthorAccountSetting(@Param("username") String username,
                                    @Param("gender") String gender,
                                    @Param("userId") String userId,
                                    Timestamp dateOfBirth

                                    );

//        @Select("""
//            UPDATE user_tb
//            SET username = #{username},
//                data_of_birth = #{dateOfBirth},
//                gender = cast(#{gender} as gender)
//                WHERE user_id = #{userId}
//            """)
//    UpdateAccountSetting updateAuthorAccountSetting(UpdateAccountSetting updateAccountSetting);


    @Select("""
            SELECT EXISTS(SELECT 1 FROM education WHERE e_id = #{educationId} AND user_id = #{userId})
            """)
    boolean checkEducationIdForCurrentAuthor(String educationId, String userId);

    @Select("""
            SELECT EXISTS(SELECT 1 FROM working_experience_tb WHERE wid = #{workingExperienceId} AND user_id = #{userId})
            """)
    boolean checkWorkingExperienceIdForCurrentAuthor(String workingExperienceId, String userId);
    @Select("""
            SELECT EXISTS(SELECT 1 FROM quote_tb WHERE q_id = #{quoteId} AND user_id = #{userId})
            """)
    boolean checkQuoteIdForCurrentAuthor(String quoteId, String userId);


    @Select("""
            UPDATE user_tb
            SET photo_url = #{photoUrl}
            WHERE user_id = #{userId}
            """)
    UpdateProfile updateProfile(String photoUrl, String userId);

    @Select("""
            SELECT EXISTS(SELECT 1 FROM user_tb WHERE user_id = #{userId})
            """)
    boolean checkUserIdExist(String userId);
}



