package com.kshrd.wearekhmer.exception;


import com.kshrd.wearekhmer.requestRequest.GenericResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@ControllerAdvice
public class DomrraCustomExceptionHandler {



    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<?> validateException(ValidateException ex , WebRequest request) {
        HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
        String instance = httpServletRequest.getRequestURI();
        String type = httpServletRequest.getRequestURL().toString();
        GenericResponse genericResponse = GenericResponse.builder()
                .type(type)
                .instance(instance)
                .statusName(ex.getHttpStatusName().name())
                .statusCode(ex.getHttpStatusNumber())
                .message(ex.getMessage())
                .title("error")
                .build();
        return ResponseEntity.status(ex.getHttpStatusNumber()).body(genericResponse);
    }

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
        Map<String ,Object> errors = new HashMap<>();
        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            if (objectError instanceof FieldError fieldError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            } else {
                errors.put(objectError.getObjectName(), objectError.getDefaultMessage());
            }
        }
        GenericResponse genericResponse =
                GenericResponse.builder()
                        .payload(errors)
                        .status("400")
                        .build();
        return ResponseEntity.badRequest().body(genericResponse);
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


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseStatusException responseStatusException(ResponseStatusException ex) {
        return new ResponseStatusException(ex.getStatusCode(), ex.getReason());
    }




    @ExceptionHandler(ErrorResponseException.class)
    public ErrorResponseException responseStatusException(ErrorResponseException ex) {
        return new ErrorResponseException(ex.getStatusCode(), ex.getBody(), ex.getCause());
    }


    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> validateException(ExpiredJwtException ex , WebRequest request) {
        HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
        String instance = httpServletRequest.getRequestURI();
        String type = httpServletRequest.getRequestURL().toString();
        GenericResponse genericResponse = GenericResponse.builder()
                .type(type)
                .instance(instance)
                .statusName(HttpStatus.UNAUTHORIZED.name())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("Token had been already expired")
                .title("error")
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(genericResponse);
    }


}
