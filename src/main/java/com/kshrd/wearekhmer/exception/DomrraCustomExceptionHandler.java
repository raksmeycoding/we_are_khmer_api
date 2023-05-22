package com.kshrd.wearekhmer.exception;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class DomrraCustomExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleDuplicateKeyException(DuplicateKeyException ex) {
        GenericResponse genericResponse = GenericResponse.builder()
                .title("duplicate email!")
                .status(String.valueOf(HttpStatus.CONFLICT.value()))
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(genericResponse);
    }
}
