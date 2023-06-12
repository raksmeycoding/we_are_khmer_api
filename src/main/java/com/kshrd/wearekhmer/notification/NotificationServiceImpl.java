package com.kshrd.wearekhmer.notification;

import com.kshrd.wearekhmer.exception.ValidateException;
import com.kshrd.wearekhmer.notification.entity.response.AuthorNotificationList;
import com.kshrd.wearekhmer.notification.entity.response.UserRequestAuthorList;
import com.kshrd.wearekhmer.notification.entity.response.ReportArticleList;
import com.kshrd.wearekhmer.notification.entity.response.ViewAuthorRequest;
import com.kshrd.wearekhmer.utils.WeAreKhmerConstant;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.validation.DefaultWeAreKhmerValidation;
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

    private DefaultWeAreKhmerValidation defaultWeAreKhmerValidation;

    @Override
    public List<Notification> getAllNotification() {
        return notificationMapper.getAllNotification();
    }


    @Override
    public Notification deleteNotificationById(String notificationId) {
        return notificationMapper.deleteNotificationById(notificationId);
    }

    @Override
    public List<UserRequestAuthorList> getAllNotificationTypeRequest(String status) {
        return notificationMapper.TypeRequest(status);
    }

//    @Override
//    public ViewAuthorRequest ViewUserRequestDetail(String userId) {
//        if(notificationMapper.validateUserIdExistInUserRequestToBeAuthor(userId)){
//            if(notificationMapper.validateAuthorPendingExist(userId)){
//                return notificationMapper.getUserRequestDetail(userId);
//            }else {
//                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User has already been author");
//            }
//        }else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "userId not found");
//        }
//    }

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
    public Integer totalRequestToBeAuthorRecords(String status) {
        return notificationMapper.totalRequestToBeAuthorRecords(status);
    }

    @Override
    public Integer totalReportArticleRecords() {
        return notificationMapper.totalReportArticleRecords();
    }

    @Override
    public Notification deleteNotificationByTypeAndId( String notificationId,String notificationType) {
        if(notificationMapper.validateNotificationIdExistInNotificationType(notificationType,notificationId))
            return notificationMapper.deleteNotificationByTypeAndId( notificationType,notificationId);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NotificationId not match NotificationType or NotificationId does not exist");
    }

    @Override
    public ViewAuthorRequest ViewUserRequestDetail(String userId, String status) {
        if(notificationMapper.validateUserRequestExist(userId,status)){
            return notificationMapper.getUserRequestDetail(userId, status);
        }
        else {
            throw new ValidateException("userId : "+userId+" does not request to be author or userId : "+ userId+ " does not exist in status : "+status,HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }

    }
}
