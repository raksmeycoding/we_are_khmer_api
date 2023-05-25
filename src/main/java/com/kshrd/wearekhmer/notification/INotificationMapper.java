package com.kshrd.wearekhmer.notification;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface INotificationMapper {

    @Select("""
            select * from notification_tb;
            """)
    List<Notification> getAllNotification();
}
