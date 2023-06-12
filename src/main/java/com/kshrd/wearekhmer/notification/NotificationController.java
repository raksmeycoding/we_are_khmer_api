package com.kshrd.wearekhmer.notification;


import com.kshrd.wearekhmer.notification.entity.response.AuthorNotificationList;
import com.kshrd.wearekhmer.notification.entity.response.UserRequestAuthorList;
import com.kshrd.wearekhmer.notification.entity.response.ReportArticleList;
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
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.net.Inet4Address;
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

    @GetMapping
    @Operation(summary = "Get all notification")
    @Hidden
    public ResponseEntity<?> getAllNotificatoin() {
        try {
            List<Notification> notificationList = notificationService.getAllNotification();
            System.out.println(notificationList);
            return ResponseEntity.ok(GenericResponse.builder()
                    .status("200")
                    .title("success")
                    .message("Get data successfully")
                    .payload(notificationService.getAllNotification())
                    .build());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(GenericResponse.builder()
                    .message(ex.getMessage())
                    .title("error")
                    .build());
        }
    }



    public ResponseEntity<?> getNotificationForCurrentAuthor(){
        return null;
    }


    @DeleteMapping("/admin/{type}/{notificationId}")
    @Operation(summary = "(Admin can delete notification)")
    @Hidden
    public ResponseEntity<?> deleteNotificationById(HttpServletRequest httpServletRequest,@PathVariable String type, @PathVariable String notificationId) {

        List<String> userRole = null;
        userRole = weAreKhmerCurrentUser.getAuthorities();

        for(String r : userRole) {
            if (r.equalsIgnoreCase("ROLE_ADMIN")) {

            }
        }

        Notification notification = notificationService.deleteNotificationById(notificationId);
        if (notification == null) {
            URI uri = URI.create(httpServletRequest.getRequestURL().toString());
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Notification with #id=" + notificationId + " does not exist.");
            problemDetail.setType(uri);
            throw new ErrorResponseException(HttpStatus.NOT_FOUND, problemDetail, null);
        }
        return ResponseEntity.ok().body(GenericResponse.builder()
                .payload(notification)
                .status("200")
                .title("success")
                .message("Notification delete successfully.")
                .build());
    }

    @GetMapping("/admin/user_request_as_author")
    @Operation(summary = "Get request notifications to be author for admin ")
    public ResponseEntity<?> getRequestNotification(){
 ;
            GenericResponse genericResponse;

            Integer totalRecords = notificationService.totalRequestToBeAuthorRecords();

            List<UserRequestAuthorList> notifications = notificationService.getAllNotificationTypeRequest();

            genericResponse = GenericResponse.builder()
                    .totalRecords(totalRecords)
                    .status("200")
                    .title("success")
                    .message("You have gotten request to be author records successfully")
                    .payload(notifications)
                    .build();

            return ResponseEntity.ok(genericResponse);

        }



    @GetMapping("/admin/{userId}")
    @Operation(summary = "View user request detail to be author (only for admin)")
    public ResponseEntity<?> ViewUserRequestAuthorDetail(
            @PathVariable("userId") String userId
    ) {

        GenericResponse genericResponse;
        ViewAuthorRequest viewAuthorRequest = notificationService.ViewUserRequestDetail(userId);

        genericResponse = GenericResponse.builder()
                .status("200")
                .title("success")
                .payload(viewAuthorRequest)
                .message("You have gotten request to be author records successfully")
                .build();
        return ResponseEntity.ok(genericResponse);
    }

    @GetMapping("/admin/report_artcle")
    @Operation(summary = "Get report article notifications for admin")
    public ResponseEntity<?> getAllReportArticle(
    ){
        GenericResponse genericResponse;

        Integer totalRecords = notificationService.totalReportArticleRecords();

            List<ReportArticleList> reportArticleLists = notificationService.getAllReportArticles();
            System.out.println(reportArticleLists);
            genericResponse = GenericResponse.builder()
                    .status("200")
                    .title("success")
                    .message("You have successfully get all article reports")
                    .payload(reportArticleLists)
                    .totalRecords(totalRecords)
                    .build();
            return ResponseEntity.ok(genericResponse);
        }

    @GetMapping("/author")
    @Operation(summary = "Get all notification for current author")
    public ResponseEntity<?> getAllNotificationForCurrentAuthor(){

        GenericResponse genericResponse;
        Integer totalRecords = notificationService.totalNotificationRecordsForCurrentAuthor(weAreKhmerCurrentUser.getUserId());
        List<AuthorNotificationList> authorNotificationLists = notificationService.getAllNotificationForCurrentAuthor(weAreKhmerCurrentUser.getUserId());

        genericResponse = GenericResponse.builder()
                .totalRecords(totalRecords)
                .status("200")
                .title("success")
                .message("You have gotten all notification records successfully")
                .payload(authorNotificationLists)
                .build();

        return ResponseEntity.ok(genericResponse);
    }

    @DeleteMapping("/author")
    @Operation(summary = "Delete a notification for current author")
    public ResponseEntity<?> deleteNotificationForCurrentAuthor(@RequestParam("notificationId") String notificationId){

        weAreKhmerValidation.validateNotificationId(weAreKhmerCurrentUser.getUserId(),notificationId);

        AuthorNotificationList authorNotificationList = notificationService.deleteNotificationForCurrentAuthorById(notificationId);

        GenericResponse genericResponse = GenericResponse.builder()
                .status("200")
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
                .status("200")
                .title("success")
                .message("You have delete a notification successfully")
                .build();
        return ResponseEntity.ok(genericResponse);
    }


}
