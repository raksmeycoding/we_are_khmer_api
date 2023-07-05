package com.kshrd.wearekhmer.userReport.service.reportUser;

import com.kshrd.wearekhmer.userReport.model.reportUser.UserReportAuthorDatabaseReponse;
import com.kshrd.wearekhmer.userReport.request.userReportAuthor.AdminIsApproveMapperRequest;
import com.kshrd.wearekhmer.userReport.request.userReportAuthor.UserReportAuthorRequest;
import com.kshrd.wearekhmer.userReport.response.ReportArticleResponse;
import com.kshrd.wearekhmer.userReport.response.ReportAuthorResponse;

import java.util.List;

public interface IUserReportAuthorService {
    UserReportAuthorDatabaseReponse insertUserReportAuthor( UserReportAuthorRequest userReportAuthorRequest);

    List<UserReportAuthorDatabaseReponse> getAllUserReportAuthor();
    UserReportAuthorDatabaseReponse deleteUserReportAuthorById(String report_id);

    List<String> adminWillApproveOrNot(AdminIsApproveMapperRequest adminIsApproveMapperRequest);


    List<ReportAuthorResponse> getAllReportAuthors(Integer pageSize, Integer nextPage);


    ReportAuthorResponse deleteReportAuthorByUserReportAuthorId(String userReportAuthorId);
}

