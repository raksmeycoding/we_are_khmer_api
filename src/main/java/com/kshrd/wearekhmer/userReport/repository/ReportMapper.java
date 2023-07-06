package com.kshrd.wearekhmer.userReport.repository;


import com.kshrd.wearekhmer.userReport.response.ReportArticleResponse;
import com.kshrd.wearekhmer.userReport.response.ReportAuthorResponse;
import com.kshrd.wearekhmer.userReport.model.reportArticle.Report;
import com.kshrd.wearekhmer.userReport.model.reportArticle.dto.ReportDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReportMapper {

    @Select("""
            with report_table_return as (
                insert into report_tb(article_id, reason, reciever_id, user_id)
                    values (#{Report.articleId}, #{Report.reason}, (
                        select user_tb.user_id from user_tb inner join user_role_tb on user_tb.user_id = user_role_tb.user_id
                                                            inner join role_tb on role_tb.role_id = user_role_tb.role_id where role_tb.name = 'ROLE_ADMIN'
                    ), #{Report.userId}) returning *
            ) select * from report_table_return;
            """)
    Report createUserReport(@Param("Report") ReportDto reportDto);


    @Select("""
            delete from report_tb where report_id = #{reportId} returning *
            """)
    Report deleteReportByIdByAdmin(String reportId);


    @Select("""
            select * from report_tb
            """)
    List<Report> getAllReportByAdmin();


    @Select("""
            select exists(select 1 from report_tb where report_tb.report_id = #{reportId})
            """)
    boolean isReportExistByIs(String reportId);


    @Select("""
            SELECT rt.*, ut.username FROM report_tb rt INNER JOIN user_tb ut on ut.user_id = rt.user_id
            ORDER BY crate_at DESC
            LIMIT #{pageSize} OFFSET #{nextPage}
            """)

    @Results(
            id = "getReportArticle",
            value = {
                    @Result(property = "articleId", column = "article_id"),
                    @Result(property = "date", column = "crate_at"),
                    @Result(property = "reportDetail", column = "reason"),
                    @Result(property = "senderName", column = "username"),

            }
    )
    List<ReportArticleResponse>getAllReportArticles(Integer pageSize, Integer nextPage);





    @Select("""
            SELECT EXISTS(SELECT 1 FROM report_tb WHERE report_id = #{reportId})
            """)
    boolean checkReportArticleId(String reportId);




    @Select("""
            WITH delete_report_article AS (
                DELETE FROM report_tb
                    WHERE report_id = #{reportId}
                    RETURNING article_id, user_id
            )
            DELETE FROM notification_tb
            WHERE (notification_type_id, sender_id) IN (
                SELECT article_id, user_id FROM delete_report_article
            );
            """)
    ReportArticleResponse deleteReportArticleByReportId(String reportId);



    @Select("""
            SELECT COUNT(*) FROM report_tb;
            """)
    Integer totalReportArticles();





}
