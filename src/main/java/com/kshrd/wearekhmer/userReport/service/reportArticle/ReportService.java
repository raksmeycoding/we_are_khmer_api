package com.kshrd.wearekhmer.userReport.service.reportArticle;

import com.kshrd.wearekhmer.userReport.model.reportArticle.Report;
import com.kshrd.wearekhmer.userReport.model.reportArticle.dto.ReportDto;
import com.kshrd.wearekhmer.userReport.response.ReportArticleResponse;
import com.kshrd.wearekhmer.userReport.response.ReportAuthorResponse;

import java.util.List;

public interface ReportService {
    Report createUserReport(ReportDto reportDto);

    Report deleteReportByIdByAdmin(String reportId);

    List<Report> getAllReportByAdmin();

    List<ReportArticleResponse> getAllReportArticles(Integer pageSize, Integer nextPage);

    ReportArticleResponse deleteReportArticleByReportId(String reportId);


}
