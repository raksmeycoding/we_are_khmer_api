package com.kshrd.wearekhmer.userReport.service.reportUser;


import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.userReport.model.reportUser.UserReportAuthorDatabaseReponse;
import com.kshrd.wearekhmer.userReport.repository.UserReportAuthorMapper;
import com.kshrd.wearekhmer.userReport.request.userReportAuthor.AdminIsApproveMapperRequest;
import com.kshrd.wearekhmer.userReport.request.userReportAuthor.UserReportAuthorRequest;
import com.kshrd.wearekhmer.userReport.response.ReportArticleResponse;
import com.kshrd.wearekhmer.userReport.response.ReportAuthorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReportAuthorServiceImpl implements IUserReportAuthorService{
    private final UserReportAuthorMapper userReportAuthorMapper;
    @Override
    public UserReportAuthorDatabaseReponse insertUserReportAuthor(UserReportAuthorRequest userReportAuthorRequest) {
        return userReportAuthorMapper.insertUserReportAuthor(userReportAuthorRequest);
    }


    @Override
    public List<UserReportAuthorDatabaseReponse> getAllUserReportAuthor() {
        return userReportAuthorMapper.getAllUserReportAuthor();
    }


    @Override
    public UserReportAuthorDatabaseReponse deleteUserReportAuthorById(String report_id) {
        return userReportAuthorMapper.deleteUserReportAuthorById(report_id);
    }


    @Override
    public List<String> adminWillApproveOrNot(AdminIsApproveMapperRequest adminIsApproveMapperRequest) {
        return userReportAuthorMapper.adminWillApproveOrNot(adminIsApproveMapperRequest);
    }


    @Override
    public List<ReportAuthorResponse> getAllReportAuthors(Integer pageSize, Integer nextPage) {
        return userReportAuthorMapper.getAllReportAuthors(pageSize,nextPage);
    }


    @Override
    public ReportAuthorResponse deleteReportAuthorByUserReportAuthorId(String userReportAuthorId) {
        if(!userReportAuthorMapper.checkReportAuthorId(userReportAuthorId))
            throw new ValidateException("There's no userReportAuthorId", HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        return userReportAuthorMapper.deleteReportAuthorByReportAuthorId(userReportAuthorId);
    }
}
