package com.kshrd.wearekhmer.userReport.service.reportArticle;


import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.userReport.model.reportArticle.Report;
import com.kshrd.wearekhmer.userReport.model.reportArticle.dto.ReportDto;
import com.kshrd.wearekhmer.userReport.repository.ReportMapper;
import com.kshrd.wearekhmer.userReport.response.ReportArticleResponse;
import com.kshrd.wearekhmer.userReport.response.ReportAuthorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportMapper reportMapper;

    @Override
    public Report createUserReport(ReportDto reportDto) {
        return reportMapper.createUserReport(reportDto);
    }


    @Override
    public List<Report> getAllReportByAdmin() {
        return reportMapper.getAllReportByAdmin();
    }

    @Override
    public Report deleteReportByIdByAdmin(String reportId) {
        return reportMapper.deleteReportByIdByAdmin(reportId);
    }

    @Override
    public List<ReportArticleResponse> getAllReportArticles(Integer pageSize, Integer nextPage) {
        return reportMapper.getAllReportArticles(pageSize,nextPage);
    }


    @Override
    public ReportArticleResponse deleteReportArticleByReportId(String reportId) {
        if(!reportMapper.checkReportArticleId(reportId))
            throw new ValidateException("There's no reportId", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        return reportMapper.deleteReportArticleByReportId(reportId);
    }


}
