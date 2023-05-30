package com.kshrd.wearekhmer.notification;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/notification")
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {
    private final INotificationService notificationService;

    @GetMapping
    @Operation(summary = "Get all notification")
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
}
