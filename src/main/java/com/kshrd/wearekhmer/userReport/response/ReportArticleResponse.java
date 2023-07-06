package com.kshrd.wearekhmer.userReport.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportArticleResponse {
    private String report_id;
    private String articleId;
    private Date date;
    private String reportDetail;

    private String senderName;

}
