package com.kshrd.wearekhmer.userReport.repository;


import com.kshrd.wearekhmer.userReport.model.Report;
import com.kshrd.wearekhmer.userReport.model.dto.ReportDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReportMapper {

    @Select("""
            with report_table_return as (
                insert into report_tb(article_id, reason, reciever_id, sender_id)
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


}
