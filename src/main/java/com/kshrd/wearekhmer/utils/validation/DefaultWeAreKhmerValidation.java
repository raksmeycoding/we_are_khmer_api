package com.kshrd.wearekhmer.utils.validation;


import com.kshrd.wearekhmer.exception.CustomRuntimeException;
import com.kshrd.wearekhmer.utils.WeAreKhmerConstant;
import com.kshrd.wearekhmer.utils.enumUtil.EGender;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("DefaultWeAreKhmerValidation")
@AllArgsConstructor
public class DefaultWeAreKhmerValidation implements WeAreKhmerValidation{

    private final WeAreKhmerConstant weAreKhmerConstant;
    @Override
    public void validateElementInAList(List<?> list, Integer x, String mssErrSizeZero, String mssErrMaxSize) {
        if (list == null || list.isEmpty()) {
            throw new  IllegalArgumentException(mssErrSizeZero);
        }
        if (list.size() > x) {
            throw new IllegalArgumentException(mssErrMaxSize);
        }

    }


    @Override
    public void validateElementLengthInAList(List<?> list, Integer x, String mssErrSizeZero, String mssErrMaxSize) {
        if (list == null || list.isEmpty()) {
            throw new  IllegalArgumentException(mssErrSizeZero);
        }
        if (list.size() > x) {
            throw new IllegalArgumentException(mssErrMaxSize);
        }
    }


    @Override
    public void genderValidation(String gender) {
        for(String g: weAreKhmerConstant.GENDER)  {
            if(gender.equals(g)){
               return;
            }
            throw new CustomRuntimeException("Gender must be lowercase and be formatted in (male, female, other).");

        }
    }
}
