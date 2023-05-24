package com.kshrd.wearekhmer.userReport.service;


import com.kshrd.wearekhmer.userReport.model.Report;
import com.kshrd.wearekhmer.userReport.model.dto.ReportDto;
import com.kshrd.wearekhmer.userReport.repository.ReportMapper;
import com.kshrd.wearekhmer.userReport.request.ReportRequest;
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
