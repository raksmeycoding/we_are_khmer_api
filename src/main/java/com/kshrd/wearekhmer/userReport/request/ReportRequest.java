package com.kshrd.wearekhmer.userReport.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReportRequest {
    private String articleId;
    private String reason;
}
