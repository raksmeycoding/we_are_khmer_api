package com.kshrd.wearekhmer.userReport.controller;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.userReport.model.reportUser.UserReportAuthorDatabaseReponse;
import com.kshrd.wearekhmer.userReport.request.userReportAuthor.AdminIsApproveMapperRequest;
import com.kshrd.wearekhmer.userReport.request.userReportAuthor.AdminIsApproveRequestBody;
import com.kshrd.wearekhmer.userReport.request.userReportAuthor.UserReportAuthorRequest;
import com.kshrd.wearekhmer.userReport.request.userReportAuthor.UserReportAuthorRequestBody;
import com.kshrd.wearekhmer.userReport.service.reportUser.IUserReportAuthorService;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceClassHelper;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/report/author")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserReportAuthorController {


    private final IUserReportAuthorService userReportAuthorService;
    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;
    private final WeAreKhmerValidation weAreKhmerValidation;

    private final ServiceClassHelper serviceClassHelper;


    @PostMapping("/{authorId}")
    @Operation(summary = "(User can report author)")
    public ResponseEntity<?> insertUserReportAuthor(HttpServletRequest httpServletRequest, @PathVariable String authorId, @RequestBody @Validated UserReportAuthorRequestBody userReportAuthorRequestBody) {
        try {
            UserReportAuthorRequest userReportAuthorRequest = UserReportAuthorRequest.builder()
                    .user_id(weAreKhmerCurrentUser.getUserId())
                    .author_id(authorId)
                    .reason(userReportAuthorRequestBody.getReason())
                    .build();
            UserReportAuthorDatabaseReponse userReportAuthorDatabaseReponse = userReportAuthorService.insertUserReportAuthor(userReportAuthorRequest);
            return ResponseEntity.ok(userReportAuthorDatabaseReponse);
        } catch (Exception ex) {
            serviceClassHelper.helpThrowPSQLException(httpServletRequest, ex);
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            URI uri = URI.create(httpServletRequest.getRequestURL().toString());
            problemDetail.setType(uri);
            throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, problemDetail, null);
        }
    }


    @GetMapping
    @Operation(summary = "(Admin Get all user report author)")
    public ResponseEntity<?> getAllUserReportAuthor() {
        List<UserReportAuthorDatabaseReponse> userReportAuthorDatabaseReponses = userReportAuthorService.getAllUserReportAuthor();
        return ResponseEntity.ok().body(GenericResponse.builder()
                .message("Get user report data successfully.")
                .title("success")
                .status("200")
                .payload(userReportAuthorDatabaseReponses)
                .build());
    }


    @DeleteMapping("/admin/{reportId}")
    @Operation(summary = "(Admin can delete user report author)")
    public ResponseEntity<?> deleteUserReportAuthorById(HttpServletRequest httpServletRequest, @PathVariable String reportId) {
        UserReportAuthorDatabaseReponse databaseReponse = userReportAuthorService.deleteUserReportAuthorById(reportId);
        if (databaseReponse == null) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "No record has been found!");
            problemDetail.setType(URI.create(httpServletRequest.getRequestURL().toString()));
            throw new ErrorResponseException(HttpStatus.NOT_FOUND, problemDetail, null);
        }
        return ResponseEntity.ok(GenericResponse.builder()
                .payload(databaseReponse)
                .status("200")
                .title("Delete successfully!")
                .build());
    }


    @PostMapping("/admin/approveOrReject/{authorId}")
    public ResponseEntity<?> adminApproveOrRejectToBandAuthor(HttpServletRequest httpServletRequest, @PathVariable String authorId, @RequestBody @Validated AdminIsApproveRequestBody adminIsApproveRequestBody) {
        try {
            weAreKhmerValidation.validateAdminIsRejectOrApprove(adminIsApproveRequestBody.getStatus());
            AdminIsApproveMapperRequest adminIsApproveMapperRequest = AdminIsApproveMapperRequest.builder()
                    .author_id(authorId)
                    .status(adminIsApproveRequestBody.getStatus())
                    .build();
            List<String> effectedRowAuthorId = userReportAuthorService.adminWillApproveOrNot(adminIsApproveMapperRequest);
            if (effectedRowAuthorId.size() == 0) {
                throw new IllegalArgumentException("No author with #id=" + authorId + " had been found!");
            }
            return ResponseEntity.ok().body(GenericResponse.builder()
                    .title("success")
                    .statusCode(200)
                    .message("Administrator updated author permission with #id= + " + authorId + " accessing DOMRRA platform.")
                    .build());
        } catch (Exception ex) {
            serviceClassHelper.helpThrowPSQLException(httpServletRequest, ex);
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
            problemDetail.setType(URI.create(httpServletRequest.getRequestURL().toString()));
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST, problemDetail, null);
        }
    }
}
