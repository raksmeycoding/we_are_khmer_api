package com.kshrd.wearekhmer.userReport.request.reportArticle;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReportRequest {
    @NotBlank(message = "articleId must be not blank.")
    @NotNull(message = "articleId must be not null.")
    private String articleId;

    @NotBlank(message = "reason must be not blank.")
    @NotNull(message = "reason must be not null.")
    private String reason;
}
