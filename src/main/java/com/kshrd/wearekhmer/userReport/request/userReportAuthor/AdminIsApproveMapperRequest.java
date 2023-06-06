package com.kshrd.wearekhmer.userReport.request.userReportAuthor;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.ErrorResponseException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminIsApproveMapperRequest {
    private String author_id;
    private String status;

}
