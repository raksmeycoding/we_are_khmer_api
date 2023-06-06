package com.kshrd.wearekhmer.userReport.model.reportUser;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class UserReportAuthorDatabaseReponse {
    private String user_report_author_id;
    private String user_id;
    private String author_id;
    private String reciever_id;
    private String reason;
    private Timestamp create_at;
    private String status;
    private String one_signal_id;
}
