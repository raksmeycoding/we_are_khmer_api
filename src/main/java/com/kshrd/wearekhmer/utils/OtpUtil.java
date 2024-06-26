package com.kshrd.wearekhmer.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Component
@AllArgsConstructor
public class OtpUtil {

    private String generatedUUid;
    private Timestamp currentDate;
    private Timestamp expiredAt;

    public OtpUtil() {

    }

    public String getGeneratedUUid() {
        Random random = new Random();
        int min = 100_000; // Minimum 6-digit number
        int max = 999_999; // Maximum 6-digit number
        int randomNumber = random.nextInt(max - min + 1) + min;
        this.generatedUUid = String.valueOf(randomNumber);
        return this.generatedUUid;
    }

    public void setGeneratedUUid(String generatedUUid) {
        this.generatedUUid = generatedUUid;
    }

    public Timestamp getCurrentDate() {
        Timestamp newTimeStamp = new Timestamp(System.currentTimeMillis());
        this.currentDate = newTimeStamp;
        return currentDate;
    }

    public void setCurrentDate(Timestamp currentDate) {
        this.currentDate = currentDate;
    }


    public Timestamp getExpiredAt() {
//        init current date
        init();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.currentDate);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Timestamp updateTimeStamp = new Timestamp(calendar.getTimeInMillis());
        this.expiredAt = updateTimeStamp;
        return expiredAt;
    }

    public void setExpiredAt(Timestamp expiredAt) {
        this.expiredAt = expiredAt;
    }


    private void init() {
        getCurrentDate();
    }
}
