package com.course.onlinecoursemanagement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalMethodException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> myMethodArgumentNotValid(MethodArgumentNotValidException e) {
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            response.put(fieldName, message);
        });

        return new ResponseEntity<>(response, e.getStatusCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
