package com.kshrd.wearekhmer.utils.validation;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("DefaultWeAreKhmerValidation")
public class DefaultWeAreKhmerValidation implements WeAreKhmerValidation{
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

    }
}
