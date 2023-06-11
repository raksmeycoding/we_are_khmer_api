package com.kshrd.wearekhmer.utils;


import org.springframework.stereotype.Component;



@Component

public class WeAreKhmerConstant {
     public static String[] WORKING_EXPERIENCE_ERROR_MESSAGE = {
            "working experience are required least 1.",
            "working experience only 3 are allowed."
    };
     public static String[] EDUCATION_ERROR_MESSAGE = {
            "education are required least 1.",
            "education only 3 are allowed."
    };

     public String[] GENDER = {
             "male", "female", "other"
     };


    public final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@#$%^&+=]).{8,}$";


    public final String[] listOfImageType = {"CATEGORY", "USER", "ARTICLE"};

    public String[] NotificationTypeAdmin = {"REPORT_ON_ARTICLE", "USER_REQUEST_AS_AUTHOR", "USER_REPORT_AUTHOR"};
}
