package com.kshrd.wearekhmer.notification;


import com.kshrd.wearekhmer.notification.entity.response.AuthorNotificationList;
import com.kshrd.wearekhmer.notification.entity.response.UserRequestAuthorList;
import com.kshrd.wearekhmer.notification.entity.response.NotificationResponse;
import com.kshrd.wearekhmer.notification.entity.response.ViewAuthorRequest;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface INotificationMapper {

    @Select("""
            select * from notification_tb;
            """)
    List<Notification> getAllNotification();


    @Select("""
            delete from notification_tb where notification_id = #{notificationId} returning *;
            """)
    Notification deleteNotificationById(String notificationId);




    @Select("""
            SELECT art.author_request_id, ut.photo_url, art.user_id, art.is_author_accepted,  ut.email ,art.author_request_name, art.createat, art.reason
            FROM author_request_tb as art INNER JOIN user_tb ut on ut.user_id = art.user_id
            WHERE is_author_accepted = cast(#{status} AS status)
            ORDER BY createat DESC LIMIT #{pageNumber} OFFSET #{nextPage};       
            """)
    @Results(id = "NotificationResponse", value = {
            @Result(property = "authorRequestId", column = "author_request_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "authorRequestName", column = "author_request_name"),
            @Result(property = "createAt", column = "createat"),
            @Result(property = "reason", column = "reason"),
            @Result(property = "email", column = "email"),
            @Result(property = "photoUrl", column = "photo_url"),
            @Result(property = "isAccepted", column = "is_author_accepted")
    })
    List<UserRequestAuthorList> TypeRequest(String status, Integer pageNumber, Integer nextPage);


    @Select("""
SELECT art.*, ut.email, ut.photo_url
FROM author_request_tb as art INNER JOIN user_tb ut on ut.user_id = art.user_id WHERE art.user_id = #{userId} AND is_author_accepted =  cast(#{status} AS status)
            """)
    @Result(property = "authorId", column = "user_id")
    @Result(property = "authorName", column = "author_request_name")
    @Result(property = "photoUrl", column = "photo_url")
    @Result(property = "email", column = "email")
    @Result(property = "workingExperience", column = "user_id", many = @Many(select = "com.kshrd.wearekhmer.user.repository.WorkingExperienceMapper.getByUserId"))
    @Result(property = "education", column = "user_id", many = @Many(select = "com.kshrd.wearekhmer.user.repository.EducationMapper.getEducationByUserIdObject"))
    @Result(property = "quote", column = "user_id", many = @Many(select = "com.kshrd.wearekhmer.user.repository.QuoteMapper.getQuoteByUserIdAsObject"))
    @Result(property = "reason", column = "reason")
    @Result(property = "isAccepted", column = "is_author_accepted")
    @Result(property = "requestOn", column = "createat")
    ViewAuthorRequest getUserRequestDetail(String userId, String status);


    @Select("""
SELECT * FROM notification_tb WHERE notification_type = 'USER_REPORT_AUTHOR' OR notification_type = 'REPORT_ON_ARTICLE' OR  notification_type = 'USER_REQUEST_AS_AUTHOR'
ORDER BY createat DESC LIMIT #{pageNumber} OFFSET #{nextPage};
;
            """)
            @Result(property = "notificationId", column = "notification_id")
            @Result(property = "notificationTypeId", column = "notification_type_id")
            @Result(property = "date", column = "createat")
            @Result(property = "profile", column = "sender_image_url")
            @Result(property = "senderName", column = "sender_name")
            @Result(property = "notificationType", column = "notification_type")
    List<NotificationResponse> getAllNotificationType(Integer pageNumber, Integer nextPage);


    @Select("""
            SELECT nt.notification_id, nt.receiver_id, notification_type, nt.notification_type_id, nt.createat, ut.photo_url, ut.username
            FROM notification_tb AS nt INNER JOIN user_tb ut on ut.user_id = nt.sender_id WHERE receiver_id = #{authorId}
            ORDER BY nt.createat DESC
            ;
            """)
    @Results(id = "authorNotification", value = {
            @Result(property = "authorId", column = "receiver_id"),
            @Result(property = "notificationId", column = "notification_id"),
            @Result(property = "notificationType", column = "notification_type"),
            @Result(property = "date", column = "createat"),
            @Result(property = "notificationTypeId", column = "notification_type_id"),
            @Result(property = "fullName", column = "username"),
            @Result(property = "photoUrl", column = "photo_url"),
    })

    List<AuthorNotificationList> getAllNotificationForCurrentAuthor(String authorId);

    @Select("""
            DELETE FROM notification_tb WHERE notification_id = #{notificationId}  AND receiver_id = #{userId}
            """)
    AuthorNotificationList deleteNotificationForCurrentAuthor(String notificationId, String userId);

    @Select("""
            SELECT EXISTS(SELECT 1 FROM notification_tb WHERE notification_id = #{notificationId} AND receiver_id = #{userId})
            """)
    boolean checkAuthorHasNotificationId(String userId, String notificationId);


    @Select("""
            SELECT count(notification_id) FROM notification_tb WHERE receiver_id = #{userId};
            """)
    Integer totalNotificationRecordForCurrentAuthor(String userId);

    @Select("""
            SELECT count(author_request_id) FROM author_request_tb WHERE is_author_accepted =  cast(#{status} AS status);
            """)
    Integer totalRequestToBeAuthorRecords(String status);

    @Select("""
            SELECT count(notification_id) FROM notification_tb WHERE notification_type = 'REPORT_ON_ARTICLE';
            """)
    Integer totalReportArticleRecords();

    @Select("""
            DELETE FROM notification_tb WHERE notification_id = #{notificationId} AND notification_type = cast(#{notificationType} AS notification_type);
            """)
    Notification deleteNotificationByTypeAndId(String notificationId , String notificationType);

    @Select("""
            SELECT EXISTS(SELECT 1 FROM notification_tb WHERE notification_id = #{notificationId} AND notification_type = cast(#{notificationType} AS notification_type));
            """)
    boolean validateNotificationIdExistInNotificationType( String notificationId, String notificationType);

   @Select("""
            SELECT EXISTS(SELECT 1 FROM notification_tb WHERE notification_id = #{notificationId})
           """)
    boolean validateNotificationId(String notificationId);


   @Select("""
           SELECT EXISTS(SELECT 1 FROM author_request_tb WHERE user_id = #{userId} AND is_author_accepted = cast(#{status} AS status));
           """)
    boolean validateUserRequestExist(String userId, String status);

   @Select("""
           SELECT count(*) FROM notification_tb WHERE notification_type = 'USER_REPORT_AUTHOR' OR notification_type = 'REPORT_ON_ARTICLE' OR  notification_type = 'USER_REQUEST_AS_AUTHOR';
           """)
   Integer totalNotificationOfAllType();


   @Select("""
           SELECT EXISTS(SELECT 1 FROM author_request_tb WHERE user_id = #{userId})
           """)
    boolean validateUserIdExistInUserRequestToBeAuthor(String userId);


   @Select("""
           SELECT * FROM notification_tb WHERE notification_type = cast(#{status} AS notification_type)
           ORDER BY createat DESC LIMIT #{pageNumber} OFFSET #{nextPage}
           """)
   @Result(property = "notificationId", column = "notification_id")
   @Result(property = "notificationTypeId", column = "notification_type_id")
   @Result(property = "date", column = "createat")
   @Result(property = "profile", column = "sender_image_url")
   @Result(property = "senderName", column = "sender_name")
   @Result(property = "notificationType", column = "notification_type")
    List<NotificationResponse> getNotificationTypeReport(Integer pageNumber, Integer nextPage, String status);

   @Select("""
           SELECT count(notification_id) FROM notification_tb WHERE notification_type = cast(#{status} AS notification_type)
           """)
    Integer totalRecordNotificationTypeReport(String status);






}
