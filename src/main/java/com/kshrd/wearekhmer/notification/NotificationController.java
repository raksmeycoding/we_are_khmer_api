package com.kshrd.wearekhmer.notification;


import com.kshrd.wearekhmer.notification.entity.response.AuthorNotificationList;
import com.kshrd.wearekhmer.notification.entity.response.UserRequestAuthorList;
import com.kshrd.wearekhmer.notification.entity.response.NotificationResponse;
import com.kshrd.wearekhmer.notification.entity.response.ViewAuthorRequest;
import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
import com.kshrd.wearekhmer.utils.serviceClassHelper.ServiceClassHelper;
import com.kshrd.wearekhmer.utils.validation.DefaultWeAreKhmerValidation;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/v1/notification")
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {
    private final INotificationService notificationService;
    private final WeAreKhmerCurrentUser weAreKhmerCurrentUser;
    private final DefaultWeAreKhmerValidation defaultWeAreKhmerValidation;

    private final WeAreKhmerValidation weAreKhmerValidation;

    private static final Integer PAGE_SIZE = 10;
    private final ServiceClassHelper serviceClassHelper;

    private final INotificationMapper notificationMapper;



    private Integer getNextPage(Integer page) {
        int numberOfRecord = serviceClassHelper.getTotalOfRecordInArticleTb();
        System.out.println(numberOfRecord);
        int totalPage = (int) Math.ceil((double) numberOfRecord / PAGE_SIZE);
        System.out.println(totalPage);
        if (page > totalPage) {
            page = totalPage;
        }
        weAreKhmerValidation.validatePageNumber(page);
        return (page - 1) * PAGE_SIZE;
    }


    @GetMapping("/admin/user-request-as-author")
    @Operation(summary = "Get request notifications to be author for admin ")
    public ResponseEntity<?> getRequestNotification(
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam("status") String status){

 ;
            GenericResponse genericResponse;

            Integer nextPage = getNextPage(page);

            weAreKhmerValidation.validateStatus(status.toUpperCase());

            Integer totalRecords = notificationService.totalRequestToBeAuthorRecords(status.toUpperCase());

            List<UserRequestAuthorList> notifications = notificationService.getAllNotificationTypeRequest(status.toUpperCase(), PAGE_SIZE, nextPage);

            if(notifications.size()>0){

                genericResponse = GenericResponse.builder()
                        .totalRecords(totalRecords)
                        .statusCode(200)
                        .title("success")
                        .message("You have gotten request to be author records successfully")
                        .payload(notifications)
                        .build();

                return ResponseEntity.ok(genericResponse);
            }else{
                genericResponse = GenericResponse.builder()
                        .totalRecords(totalRecords)
                        .statusCode(404)
                        .title("failure")
                        .message("There's no user-request to be author records in list")
                        .build();

                return ResponseEntity.ok(genericResponse);
            }


        }



    @GetMapping("/admin")
    @Operation(summary = "View user request detail to be author (only for admin)")
    public ResponseEntity<?> ViewUserRequestAuthorDetail(
            @RequestParam("status") String status,
            @RequestParam("userId") String userId

    ) {
        weAreKhmerValidation.validateStatus(status.toUpperCase());
        GenericResponse genericResponse;
        ViewAuthorRequest viewAuthorRequest = notificationService.ViewUserRequestDetail(userId,status.toUpperCase());

        genericResponse = GenericResponse.builder()
                .statusCode(200)
                .title("success")
                .payload(viewAuthorRequest)
                .message("You have gotten request to be author records successfully")
                .build();
        return ResponseEntity.ok(genericResponse);
    }

    @GetMapping("/admin/all-notification-type")
    @Operation(summary = "Get all notifications for admin")
    public ResponseEntity<?> getAllNotification(
            @RequestParam(defaultValue = "1" , required = false) Integer page
    ){


        GenericResponse genericResponse;
        Integer nextPage = getNextPage(page);

        Integer totalRecords = notificationService.totalNotificationOfAllType();

            List<NotificationResponse> notificationResponses = notificationService.getAllNotificationType(
                    PAGE_SIZE,
                    nextPage
            );
            System.out.println(notificationResponses);

            if(notificationResponses.size()>0){
                genericResponse = GenericResponse.builder()
                        .statusCode(200)
                        .title("success")
                        .message("You have successfully get all notifications")
                        .payload(notificationResponses)
                        .totalRecords(totalRecords)
                        .build();
                return ResponseEntity.ok(genericResponse);
            }else{
                genericResponse = GenericResponse.builder()
                        .statusCode(404)
                        .title("failure")
                        .message("There's no notification records in list")
                        .totalRecords(totalRecords)
                        .build();
                return ResponseEntity.ok(genericResponse);
            }

        }

    @GetMapping("/author")
    @Operation(summary = "Get all notification for current author")
    public ResponseEntity<?> getAllNotificationForCurrentAuthor(){

        GenericResponse genericResponse;
        Integer totalRecords = notificationService.totalNotificationRecordsForCurrentAuthor(weAreKhmerCurrentUser.getUserId());
        List<AuthorNotificationList> authorNotificationLists = notificationService.getAllNotificationForCurrentAuthor(weAreKhmerCurrentUser.getUserId());

        if(authorNotificationLists.size()>0){
            genericResponse = GenericResponse.builder()
                    .totalRecords(totalRecords)
                    .statusCode(200)
                    .title("success")
                    .message("You have gotten all notification records successfully")
                    .payload(authorNotificationLists)
                    .build();

            return ResponseEntity.ok(genericResponse);
        }
        genericResponse = GenericResponse.builder()
                .statusCode(404)
                .title("failure")
                .message("There's no notification in list")
                .build();
        return ResponseEntity.ok(genericResponse);


    }

    @DeleteMapping("/author")
    @Operation(summary = "Delete a notification for current author")
    public ResponseEntity<?> deleteNotificationForCurrentAuthor(@RequestParam("notificationId") String notificationId){

        weAreKhmerValidation.validateNotificationId(weAreKhmerCurrentUser.getUserId(),notificationId);

        AuthorNotificationList authorNotificationList = notificationService.deleteNotificationForCurrentAuthorById(notificationId);

        GenericResponse genericResponse = GenericResponse.builder()
                .statusCode(200)
                .title("success")
                .message("You have deleted this notification successfully")
                .payload(authorNotificationList)
                .build();
        return ResponseEntity.ok(genericResponse);
    }
    @DeleteMapping("/admin")
    @Operation(summary = "Delete notification by type and id for admin")
    public ResponseEntity<?> deleteNotificationByTypeAndId(
            @RequestParam("notificationType") String notificationType,
            @RequestParam("notificationId") String notificationId


    ){
        defaultWeAreKhmerValidation.validateNotificationTypeAdmin(notificationType);
        Notification notification = notificationService.deleteNotificationByTypeAndId(notificationType,notificationId);
        GenericResponse genericResponse = GenericResponse.builder()
                .statusCode(200)
                .title("success")
                .message("You have delete a notification successfully")
                .build();
        return ResponseEntity.ok(genericResponse);
    }

    @GetMapping("/admin/type")
    @Operation(summary = "Get report notification by status for admin")
    public ResponseEntity<?> getReportNotificationByStatus(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam("status") String status){
        GenericResponse genericResponse;
        Integer nextPage = getNextPage(page);
        weAreKhmerValidation.validateReportType(status.toUpperCase());

        List<NotificationResponse> responseList = notificationService.getNotificationTypeReport(PAGE_SIZE,nextPage,status.toUpperCase());
        Integer totalRecord = notificationMapper.totalRecordNotificationTypeReport(status);


        if(responseList.size()>0){
            genericResponse = GenericResponse.builder()
                    .totalRecords(totalRecord)
                    .statusCode(200)
                    .payload(responseList)
                    .title("success")
                    .message("You have gotten notification reports successfully")
                    .build();
            return ResponseEntity.ok(genericResponse);
        }

        genericResponse = GenericResponse.builder()
                .statusCode(404)
                .title("failure")
                .message("There's no notification in list")
                .build();
        return ResponseEntity.ok(genericResponse);


    }


}
