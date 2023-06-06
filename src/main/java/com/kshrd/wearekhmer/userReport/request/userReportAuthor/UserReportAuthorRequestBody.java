package com.kshrd.wearekhmer.userReport.request.userReportAuthor;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class UserReportAuthorRequestBody {
    private String reason;
}
