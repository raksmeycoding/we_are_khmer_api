package com.kshrd.wearekhmer.notification;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class NotificationServiceImpl implements INotificationService {
    private final INotificationMapper notificationMapper;
    @Override
    public List<Notification> getAllNotification() {
        return notificationMapper.getAllNotification();
    }


    @Override
    public Notification deleteNotificationById(String notificationId) {
        return notificationMapper.deleteNotificationById(notificationId);
    }
}
