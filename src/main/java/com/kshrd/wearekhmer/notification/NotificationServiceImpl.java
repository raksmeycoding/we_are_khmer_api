package com.kshrd.wearekhmer.notification;

import com.kshrd.wearekhmer.notification.entity.response.AuthorNotificationList;
import com.kshrd.wearekhmer.notification.entity.response.UserRequestAuthorList;
import com.kshrd.wearekhmer.notification.entity.response.ReportArticleList;
import com.kshrd.wearekhmer.notification.entity.response.ViewAuthorRequest;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@AllArgsConstructor
@Service
public class NotificationServiceImpl implements INotificationService {
    private final INotificationMapper notificationMapper;

    private WeAreKhmerCurrentUser weAreKhmerCurrentUser;
    @Override
    public List<Notification> getAllNotification() {
        return notificationMapper.getAllNotification();
    }


    @Override
    public Notification deleteNotificationById(String notificationId) {
        return notificationMapper.deleteNotificationById(notificationId);
    }

    @Override
    public List<UserRequestAuthorList> getAllNotificationTypeRequest() {
        return notificationMapper.TypeRequest();
    }

    @Override
    public ViewAuthorRequest ViewUserRequestDetail(String userId) {
        return notificationMapper.getUserRequestDetail(userId);
    }

    @Override
    public List<ReportArticleList> getAllReportArticles() {
        return notificationMapper.getAllReportArticle();
    }

    @Override
    public List<AuthorNotificationList> getAllNotificationForCurrentAuthor(String authorId) {
        return notificationMapper.getAllNotificationForCurrentAuthor(authorId);
    }

    @Override
    public AuthorNotificationList deleteNotificationForCurrentAuthorById(String notificationId) {
        return notificationMapper.deleteNotificationForCurrentAuthor(notificationId, weAreKhmerCurrentUser.getUserId());
    }

    @Override
    public Integer totalNotificationRecordsForCurrentAuthor(String userId) {
        return notificationMapper.totalNotificationRecordForCurrentAuthor(weAreKhmerCurrentUser.getUserId());
    }

    @Override
    public Integer totalRequestToBeAuthorRecords() {
        return notificationMapper.totalRequestToBeAuthorRecords();
    }

    @Override
    public Integer totalReportArticleRecords() {
        return notificationMapper.totalReportArticleRecords();
    }
}
