package com.kshrd.wearekhmer.notification;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/notification")
@RestController
@AllArgsConstructor
public class NotificationController {
    private final INotificationService notificationService;

    @GetMapping
    @Operation(summary = "Get all notification")
    public ResponseEntity<?> getAllNotificatoin() {
        try {
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
