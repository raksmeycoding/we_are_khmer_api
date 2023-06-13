package com.kshrd.wearekhmer.notification;

import com.kshrd.wearekhmer.notification.entity.response.AuthorNotificationList;
import com.kshrd.wearekhmer.notification.entity.response.UserRequestAuthorList;
import com.kshrd.wearekhmer.notification.entity.response.NotificationResponse;
import com.kshrd.wearekhmer.notification.entity.response.ViewAuthorRequest;

import java.util.List;

public interface INotificationService {
    List<Notification> getAllNotification();

    Notification deleteNotificationById(String notificationId);

    List<UserRequestAuthorList> getAllNotificationTypeRequest(String status);

//    ViewAuthorRequest ViewUserRequestDetail(String userId);

    ViewAuthorRequest ViewUserRequestDetail(String userId, String status);

    List<NotificationResponse> getAllNotificationType();

    List<AuthorNotificationList> getAllNotificationForCurrentAuthor(String authorId);

    AuthorNotificationList deleteNotificationForCurrentAuthorById(String notificationId);

    Integer totalNotificationRecordsForCurrentAuthor(String userId);
    Integer totalRequestToBeAuthorRecords(String status);

    Integer totalReportArticleRecords();


    Integer totalNotificationOfAllType();

    Notification deleteNotificationByTypeAndId( String notificationId, String notificationType);



}
