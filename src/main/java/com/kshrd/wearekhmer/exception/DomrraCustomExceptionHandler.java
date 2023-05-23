package com.kshrd.wearekhmer.exception;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()){
            if(objectError instanceof FieldError fieldError) {
                errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            } else {
                errors.add(objectError.getDefaultMessage());
            }
        }
        GenericResponse genericResponse =
                GenericResponse.builder()
                        .payload(errors)
                        .status("400")
                        .build();
        return ResponseEntity.ok(genericResponse);
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<?> customRuntimeException(CustomRuntimeException ex) {
        GenericResponse genericResponse =
                GenericResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.name())
                        .title("error")
                        .message(ex.getMessage())
                        .build();
        return ResponseEntity.badRequest().body(genericResponse);

    }
}
