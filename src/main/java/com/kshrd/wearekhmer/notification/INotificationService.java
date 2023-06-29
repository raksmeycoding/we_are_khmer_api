package com.kshrd.wearekhmer.notification;

import com.kshrd.wearekhmer.notification.entity.response.*;

import java.util.List;

public interface INotificationService {
    List<Notification> getAllNotification();

    Notification deleteNotificationById(String notificationId);

    List<UserRequestAuthorList> getAllNotificationTypeRequest(String status, Integer pageNumber, Integer nextPage);

//    ViewAuthorRequest ViewUserRequestDetail(String userId);

    ViewAuthorRequest ViewUserRequestDetail(String userId, String status);

    List<NotificationResponse> getAllNotificationType(Integer pageNumber, Integer nextPage);

    List<AuthorNotificationList> getAllNotificationForCurrentAuthor(String authorId);

    AuthorNotificationList deleteNotificationForCurrentAuthorById(String notificationId);

    Integer totalNotificationRecordsForCurrentAuthor(String userId);
    Integer totalRequestToBeAuthorRecords(String status);

    Integer totalReportArticleRecords();


    Integer totalNotificationOfAllType();

    Notification deleteNotificationByTypeAndId( String notificationId, String notificationType);

    List<NotificationResponse> getNotificationTypeReport(Integer pageNumber, Integer nextPage, String status);

    ReadNotification readNotification(String notificationId, String userId);

    List<ReadNotification> readAllNotifications(String userId);



}
