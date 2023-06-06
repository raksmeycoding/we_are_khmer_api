package com.kshrd.wearekhmer.userReport.service.reportArticle;


import com.kshrd.wearekhmer.userReport.model.reportArticle.Report;
import com.kshrd.wearekhmer.userReport.model.reportArticle.dto.ReportDto;
import com.kshrd.wearekhmer.userReport.repository.ReportMapper;
import lombok.AllArgsConstructor;
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
}
