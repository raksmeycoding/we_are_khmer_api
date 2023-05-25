package com.kshrd.wearekhmer.notification;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Notification {
    private String notification_id;
    private String createat;
    private String sender_id;
    private String receiver_id;
    private String notification_type;
}
