package com.kshrd.wearekhmer.notification.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorNotificationList {
    private String authorId;
    private String notificationId;
    private String notificationType;
    private Date date;
    private String notificationTypeId;
    private String fullName;
    private String photoUrl;

    private Boolean read;

}
