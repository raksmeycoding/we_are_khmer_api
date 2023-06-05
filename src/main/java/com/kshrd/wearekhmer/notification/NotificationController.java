package com.kshrd.wearekhmer.notification;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import com.kshrd.wearekhmer.utils.WeAreKhmerCurrentUser;
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


    @DeleteMapping("/{type}/{notificationId}")
    @Operation(summary = "(Admin can delete notification)")
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
}
