package com.kshrd.wearekhmer;

import com.kshrd.wearekhmer.utils.validation.DefaultWeAreKhmerValidation;
import com.kshrd.wearekhmer.utils.validation.WeAreKhmerValidation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Calendar;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestClass {



    @Test
    public void testGetYesterdayDate() {
        Calendar yesterday = Calendar.getInstance();
        System.out.println(yesterday);
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterdayDate = new Date(yesterday.getTime().getTime());

        // Assertion to check if the obtained date is correct
        System.out.println(yesterdayDate);
        assertEquals(yesterdayDate.toString(), getYesterdayDate().toString());
    }

    private Date getYesterdayDate() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        return new Date(yesterday.getTime().getTime());
    }


}

