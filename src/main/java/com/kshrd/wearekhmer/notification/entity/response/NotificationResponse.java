package com.kshrd.wearekhmer.notification.entity.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reason;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean read;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationResponse that = (NotificationResponse) o;
        return Objects.equals(notificationId, that.notificationId) &&
                Objects.equals(notificationTypeId, that.notificationTypeId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(profile, that.profile) &&
                Objects.equals(senderName, that.senderName) &&
                Objects.equals(notificationType, that.notificationType) &&
                Objects.equals(read, that.read);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId, notificationTypeId, date, profile, senderName, notificationType, read);
    }







}
