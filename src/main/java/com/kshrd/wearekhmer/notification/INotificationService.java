package com.kshrd.wearekhmer.notification;

import com.kshrd.wearekhmer.notification.entity.response.AuthorNotificationList;
import com.kshrd.wearekhmer.notification.entity.response.UserRequestAuthorList;
import com.kshrd.wearekhmer.notification.entity.response.ReportArticleList;
import com.kshrd.wearekhmer.notification.entity.response.ViewAuthorRequest;

import java.util.List;

public interface INotificationService {
    List<Notification> getAllNotification();

    Notification deleteNotificationById(String notificationId);

    List<UserRequestAuthorList> getAllNotificationTypeRequest();

    ViewAuthorRequest ViewUserRequestDetail(String userId);

    List<ReportArticleList> getAllReportArticles();

    List<AuthorNotificationList> getAllNotificationForCurrentAuthor(String authorId);

    AuthorNotificationList deleteNotificationForCurrentAuthorById(String notificationId);

    Integer totalNotificationRecordsForCurrentAuthor(String userId);
    Integer totalRequestToBeAuthorRecords();

    Integer totalReportArticleRecords();



}
