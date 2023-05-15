package com.kshrd.wearekhmer.utils.validation;

import java.util.List;

public interface WeAreKhmerValidation {
    void validateElementInAList(List<?> list, Integer x, String mssErrSizeZero, String mssErrMaxSize);
    void validateElementLengthInAList(List<?> list, Integer x, String mssErrSizeZero, String mssErrMaxSize);

    void genderValidation(String gender);
}
