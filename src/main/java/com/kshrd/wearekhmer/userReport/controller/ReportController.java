package com.kshrd.wearekhmer.userReport.controller;


import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.userReport.model.Report;
import com.kshrd.wearekhmer.userReport.model.dto.ReportDto;
import com.kshrd.wearekhmer.userReport.request.ReportRequest;
import com.kshrd.wearekhmer.userReport.service.ReportService;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/report")
@SecurityRequirement(name = "bearerAuth")
public class ReportController {
    private final ReportService reportService;
    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;

    private final WeAreKhmerValidation weAreKhmerValidation;


    @Operation(summary = "Only user login can report article (current user can report.)")
    @PostMapping
    private ResponseEntity<?> createUserReport(@RequestBody @Valid ReportRequest reportRequest) {
        GenericResponse genericResponse;

//        validate article id;
        if (!weAreKhmerValidation.validateArticleId(reportRequest.getArticleId())) {
            throw new CustomRuntimeException("This article id :" + reportRequest.getArticleId() + " is not exist.");
        }

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


    @DeleteMapping("{reportId}")
    @Operation(summary = "(Only admin can delete this report.)")
    public ResponseEntity<?> deleteReportByIdByAdmin(@PathVariable String reportId) {
        try {
            if (!weAreKhmerValidation.isAdmin()) {
                throw new CustomRuntimeException("Sorry you are not administrator.");
            }
            if (!weAreKhmerValidation.validateReportId(reportId)) {
                throw new CustomRuntimeException("Report with id :" + reportId + " is not present.");
            }

            Report report = reportService.deleteReportByIdByAdmin(reportId);

            return ResponseEntity.ok(GenericResponse.builder()
                    .title("success")
                    .status("200")
                    .payload(report)
                    .message("report delete successfully.")
                    .build());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(GenericResponse.builder().title("error.").status("500").message(exception.getMessage()).build());
        }
    }


    @GetMapping
    @Operation(summary = "Get all reports")
    public ResponseEntity<?> getAllReportOnlyByAdmin() {
        try {
            return ResponseEntity.ok(GenericResponse.builder()
                    .payload(reportService.getAllReportByAdmin())
                    .status("200")
                    .title("success")
                    .message("Get successfully.")
                    .build());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.builder()
                    .message(ex.getMessage())
                    .title("error")
                    .status("500")
                    .build());
        }
    }
}
