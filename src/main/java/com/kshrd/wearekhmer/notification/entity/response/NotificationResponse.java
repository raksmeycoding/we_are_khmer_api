package com.kshrd.wearekhmer.notification.entity.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private String notificationId;
    private String notificationTypeId;
    private Date date;

    private String profile;
    private String senderName;

    private String notificationType;
}
