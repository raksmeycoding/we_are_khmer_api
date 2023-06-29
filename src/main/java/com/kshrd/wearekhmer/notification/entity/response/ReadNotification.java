package com.kshrd.wearekhmer.notification.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadNotification {
    private boolean read;

    private String notificationId;
    private String readerId;
}
