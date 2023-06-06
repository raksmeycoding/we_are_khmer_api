package com.kshrd.wearekhmer.userReport.service.reportUser;

import com.kshrd.wearekhmer.userReport.model.reportUser.UserReportAuthorDatabaseReponse;
import com.kshrd.wearekhmer.userReport.request.userReportAuthor.UserReportAuthorRequest;

import java.util.List;

public interface IUserReportAuthorService {
    UserReportAuthorDatabaseReponse insertUserReportAuthor( UserReportAuthorRequest userReportAuthorRequest);

    List<UserReportAuthorDatabaseReponse> getAllUserReportAuthor();
    UserReportAuthorDatabaseReponse deleteUserReportAuthorById(String report_id);
}

