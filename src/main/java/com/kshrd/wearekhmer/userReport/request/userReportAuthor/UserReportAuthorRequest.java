package com.kshrd.wearekhmer.userReport.request.userReportAuthor;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReportAuthorRequest {

    private String user_id;
    private String author_id;
    private String reason;
}
