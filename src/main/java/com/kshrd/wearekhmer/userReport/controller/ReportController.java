package com.kshrd.wearekhmer.userReport.controller;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.userReport.model.reportArticle.Report;
import com.kshrd.wearekhmer.userReport.model.reportArticle.dto.ReportDto;
import com.kshrd.wearekhmer.userReport.repository.ReportMapper;
import com.kshrd.wearekhmer.userReport.request.reportArticle.ReportRequest;
import com.kshrd.wearekhmer.userReport.response.ReportArticleResponse;
import com.kshrd.wearekhmer.userReport.response.ReportAuthorResponse;
import com.kshrd.wearekhmer.userReport.service.reportArticle.ReportService;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceClassHelper;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/report")
@SecurityRequirement(name = "bearerAuth")
public class ReportController {
    private final ReportService reportService;
    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    private final WeAreKhmerValidation weAreKhmerValidation;
    private final ReportMapper reportMapper;

    private final ServiceClassHelper serviceClassHelper;

    private final static Integer PAGE_SIZE = 10;

    private Integer getNextPage(Integer page) {
        int numberOfRecord = serviceClassHelper.getTotalOfRecordInArticleTb();
        System.out.println(numberOfRecord);
        int totalPage = (int) Math.ceil((double) numberOfRecord / PAGE_SIZE);
        System.out.println(totalPage);
        if (page > totalPage) {
            page = totalPage;
        }
        weAreKhmerValidation.validatePageNumber(page);
        return (page - 1) * PAGE_SIZE;
    }


    @Operation(summary = "Only user login can report article (current user can report.)")
    @PostMapping
    private ResponseEntity<?> createUserReport(@RequestBody @Valid ReportRequest reportRequest) {
        GenericResponse genericResponse;

        weAreKhmerValidation.validateArticleId(reportRequest.getArticleId());

        try {
            Report report = reportService.createUserReport(new ReportDto(reportRequest.getArticleId(), reportRequest.getReason(), weAreKhmerCurrentUser.getUserId()));
            genericResponse = GenericResponse.builder()
                    .status("200")
                    .message("report successfully.")
                    .title("success")
                    .payload(report)
                    .build();

            return ResponseEntity.ok(genericResponse);

        } catch (Exception exception) {
            genericResponse = GenericResponse.builder()
                    .status("500")
                    .message(exception.getMessage())
                    .title("error")
                    .build();
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body(genericResponse);
        }

    }


//    @DeleteMapping("{reportId}")
//    @Operation(summary = "(Only admin can delete this report.)")
//    public ResponseEntity<?> deleteReportByIdByAdmin(@PathVariable String reportId) {
//        weAreKhmerValidation.validateReportId(reportId);
//        try {
//            Report report = reportService.deleteReportByIdByAdmin(reportId);
//
//            return ResponseEntity.ok(GenericResponse.builder()
//                    .title("success")
//                    .status("200")
//                    .payload(report)
//                    .message("report delete successfully.")
//                    .build());
//        } catch (Exception exception) {
//            return ResponseEntity.internalServerError().body(GenericResponse.builder().title("error.").status("500").message(exception.getMessage()).build());
//        }
//    }


//    @GetMapping
//    @Operation(summary = "Get all reports")
//    public ResponseEntity<?> getAllReportOnlyByAdmin() {
//        try {
//            return ResponseEntity.ok(GenericResponse.builder()
//                    .payload(reportService.getAllReportByAdmin())
//                    .status("200")
//                    .title("success")
//                    .message("Get successfully.")
//                    .build());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return ResponseEntity.internalServerError().body(GenericResponse.builder()
//                    .message(ex.getMessage())
//                    .title("error")
//                    .status("500")
//                    .build());
//        }
//    }

    @GetMapping
    @Operation(summary = "Get articles report for admin")
    public ResponseEntity<?> getAllReportArticles(@RequestParam(required = false, defaultValue = "1") Integer page){

        Integer nextPage= getNextPage(page);
        Integer totalReportArticles = reportMapper.totalReportArticles();

        List<ReportArticleResponse> reportArticleResponses = reportService.getAllReportArticles(PAGE_SIZE,nextPage);
        GenericResponse genericResponse;
        if(reportArticleResponses.size()>0){
            genericResponse = GenericResponse.builder()
                    .totalRecords(totalReportArticles)
                    .title("success")
                    .message("You have successfully gotten report articles")
                    .statusCode(200)
                    .payload(reportArticleResponses)
                    .build();
            return ResponseEntity.ok(genericResponse);
        }
        genericResponse = GenericResponse.builder()
                .title("failure")
                .message("There's no report articles")
                .statusCode(404)
                .build();
        return ResponseEntity.ok(genericResponse);

    }


    @DeleteMapping
    @Operation(summary = "Delete report article by reportId for admin")
    public ResponseEntity<?> deleteReportArticleByReportId(@RequestParam("reportId") String reportId){

        ReportArticleResponse reportArticleResponse = reportService.deleteReportArticleByReportId(reportId);

        GenericResponse genericResponse = GenericResponse.builder()
                .statusCode(200)
                .title("success")
                .message("You have deleted this report article successfully")
                .build();
        return ResponseEntity.ok(genericResponse);

    }



}
