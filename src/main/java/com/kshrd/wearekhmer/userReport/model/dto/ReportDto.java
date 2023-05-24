package com.kshrd.wearekhmer.userReport.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReportDto {
    private String articleId;
    private String reason;
    private String senderId;
}
