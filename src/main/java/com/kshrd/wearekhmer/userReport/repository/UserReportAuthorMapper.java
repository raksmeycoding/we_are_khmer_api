package com.kshrd.wearekhmer.userReport.repository;

import com.kshrd.wearekhmer.userReport.model.reportUser.UserReportAuthorDatabaseReponse;
import com.kshrd.wearekhmer.userReport.request.userReportAuthor.AdminIsApproveMapperRequest;
import com.kshrd.wearekhmer.userReport.request.userReportAuthor.UserReportAuthorRequest;
import com.kshrd.wearekhmer.userReport.response.ReportAuthorResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserReportAuthorMapper {


    @Select("""
            select *
            from insert_user_user_report_to_author(#{user_id}, #{author_id},
                                                       #{reason});
            """)
    UserReportAuthorDatabaseReponse insertUserReportAuthor(UserReportAuthorRequest userReportAuthorRequest);


    @Select("""
            select * from user_report_author_tb order by create_at desc
            """)
    List<UserReportAuthorDatabaseReponse> getAllUserReportAuthor();


    @Select("""
            delete from user_report_author_tb where user_report_author_id = #{report_id} returning *
            """)
    UserReportAuthorDatabaseReponse deleteUserReportAuthorById(String report_id);


    @Select("""
            update user_report_author_tb urab
            set status = cast(#{status} as status)
            where urab.author_id = #{author_id} returning urab.author_id;
            """)
    List<String> adminWillApproveOrNot(AdminIsApproveMapperRequest adminIsApproveMapperRequest);

    @Select("""
            select exists(select 1 from user_tb u where u.user_id = #{userId} and is_enable = false);
            """)
    boolean isAuthorOrUserHadBennAlreadyBan(String userId);


    @Select("""
            SELECT urat.*, ut.username FROM user_report_author_tb urat INNER JOIN user_tb ut on ut.user_id = urat.user_id
            ORDER BY create_at DESC
            LIMIT #{pageSize} OFFSET #{nextPage}
            """)
    @Results(
            id = "getReportAuthors",
            value = {
                    @Result(property = "authorId", column = "author_id"),
                    @Result(property = "date", column = "create_at"),
                    @Result(property = "reportDetail", column = "reason"),
                    @Result(property = "senderName", column = "username"),
                    @Result(property = "userReportAuthorId", column = "user_report_author_id")
            }
    )
    List<ReportAuthorResponse>getAllReportAuthors(Integer pageSize, Integer nextPage);


    @Select("""
            SELECT EXISTS(SELECT 1 FROM user_report_author_tb WHERE user_report_author_id = #{userReportAuthorId})
            """)
    boolean checkReportAuthorId(String userReportAuthorId);


    @Select("""
            WITH delete_report_author AS(
                DELETE FROM user_report_author_tb
                WHERE user_report_author_id = #{userReportAuthorId}
                RETURNING author_id, user_id
            )
            DELETE FROM notification_tb
            WHERE(notification_type_id, sender_id) IN (
                SELECT author_id, user_id FROM delete_report_author
                )
            """)
    ReportAuthorResponse deleteReportAuthorByReportAuthorId(String userReportAuthorId);

    @Select("""
            SELECT COUNT(*) FROM user_report_author_tb;
            """)
    Integer totalReportAuthors();
}
