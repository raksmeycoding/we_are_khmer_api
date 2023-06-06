package com.kshrd.wearekhmer.userReport.model.reportArticle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;


@AllArgsConstructor
@Builder
@Data
public class Report {
    private String report_id;
    private String article_id;
    private Timestamp crate_at;
    private String reason;
    private String reciever_id;
    private String sender_id;
}
