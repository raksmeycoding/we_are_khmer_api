package com.kshrd.wearekhmer.exception;

import org.springframework.http.HttpStatus;

public class ValidateException extends RuntimeException {

    private HttpStatus httpStatusName;
    private Integer httpStatusNumber;

    public ValidateException(String message, HttpStatus httpStatusName, Integer httpStatusNumber) {
        super(message);
        this.httpStatusName = httpStatusName;
        this.httpStatusNumber = httpStatusNumber;

    }


    public HttpStatus getHttpStatusName() {
        return httpStatusName;
    }

    public Integer getHttpStatusNumber() {
        return httpStatusNumber;
    }
}
