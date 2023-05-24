package com.kshrd.wearekhmer.userReport.service;

import com.kshrd.wearekhmer.userReport.model.Report;
import com.kshrd.wearekhmer.userReport.model.dto.ReportDto;
import com.kshrd.wearekhmer.userReport.request.ReportRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportService {
    Report createUserReport(ReportDto reportDto);

    Report deleteReportByIdByAdmin(String reportId);

    List<Report> getAllReportByAdmin();
}
