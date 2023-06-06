package com.kshrd.wearekhmer.userReport.repository;

import com.kshrd.wearekhmer.userReport.model.reportUser.UserReportAuthorDatabaseReponse;
import com.kshrd.wearekhmer.userReport.request.userReportAuthor.UserReportAuthorRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
