package com.kshrd.wearekhmer.notification.entity.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportArticleList {
    private String reportId;
    private String articleId;
    private Date date;
    private String reason;
    private String notificationType;
}
