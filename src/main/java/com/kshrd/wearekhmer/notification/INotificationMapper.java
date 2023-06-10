package com.kshrd.wearekhmer.notification;


import com.kshrd.wearekhmer.notification.entity.response.AuthorNotificationList;
import com.kshrd.wearekhmer.notification.entity.response.UserRequestAuthorList;
import com.kshrd.wearekhmer.notification.entity.response.ReportArticleList;
import com.kshrd.wearekhmer.notification.entity.response.ViewAuthorRequest;
import org.apache.ibatis.annotations.*;

import javax.ws.rs.DELETE;
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
            SELECT art.author_request_id, ut.photo_url, art.user_id, ut.email ,art.author_request_name, art.createat, art.reason
            FROM author_request_tb as art INNER JOIN user_tb ut on ut.user_id = art.user_id
            WHERE is_author_accepted = false
            ORDER BY createat DESC;       
            """)
    @Results(id = "NotificationResponse", value = {
            @Result(property = "authorRequestId", column = "author_request_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "authorRequestName", column = "author_request_name"),
            @Result(property = "createAt", column = "createat"),
            @Result(property = "reason", column = "reason"),
            @Result(property = "email", column = "email"),
            @Result(property = "photoUrl", column = "photo_url")
    })
    List<UserRequestAuthorList> TypeRequest();


    @Select("""
            SELECT art.author_request_name,ut.email,  art.user_id, art.createat, art.reason, q_name, e_name, wet.w_name
            FROM author_request_tb as art INNER JOIN quote_tb qt on art.user_id = qt.user_id
            INNER JOIN education e on art.user_id = e.user_id
            INNER JOIN working_experience_tb wet on art.user_id = wet.user_id
            INNER JOIN user_tb ut on art.user_id = ut.user_id
            WHERE art.user_id =  #{userId}
            """)


    @Result(property = "authorId", column = "user_id")
    @Result(property = "authorName", column = "author_request_name")
    @Result(property = "photoUrl", column = "photo_url")
    @Result(property = "reason", column = "reason")
    @Result(property = "email", column = "email")
    @Result(property = "workingExperience", column = "user_id", many = @Many(select = "com.kshrd.wearekhmer.user.repository.WorkingExperienceMapper.getByUserId"))
    @Result(property = "education", column = "user_id", many = @Many(select = "com.kshrd.wearekhmer.user.repository.EducationMapper.getEducationByUserIdObject"))
    @Result(property = "quote", column = "user_id", many = @Many(select = "com.kshrd.wearekhmer.user.repository.QuoteMapper.getQuoteByUserIdAsObject"))
    ViewAuthorRequest getUserRequestDetail(String userId);




    @Select("""
            SELECT rt.report_id, rt.article_id, nt.createat, rt.reason,nt.notification_type FROM report_tb as rt INNER JOIN notification_tb nt on rt.reciever_id = nt.receiver_id
            WHERE nt.notification_type = 'REPORT_ON_ARTICLE' ORDER BY nt.createat DESC  ;
            """)
            @Result(property = "reportId", column = "report_id")
            @Result(property = "articleId", column = "article_id")
            @Result(property = "date", column = "createat")
            @Result(property = "reason", column = "reason")
            @Result(property = "notificationType", column = "notification_type")
    List<ReportArticleList> getAllReportArticle();


    @Select("""
            SELECT nt.notification_id, notification_type, nt.notification_type_id, nt.createat, ut.photo_url, ut.username
            FROM notification_tb AS nt INNER JOIN user_tb ut on ut.user_id = nt.sender_id WHERE receiver_id = #{authorId}
            ORDER BY nt.createat DESC
            ;
            """)
    @Results(id = "authorNotification", value = {
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
            SELECT count(author_request_id) FROM author_request_tb WHERE is_author_accepted = false;
            """)
    Integer totalRequestToBeAuthorRecords();

    @Select("""
            SELECT count(notification_id) FROM notification_tb WHERE notification_type = 'REPORT_ON_ARTICLE';
            """)
    Integer totalReportArticleRecords();







}
