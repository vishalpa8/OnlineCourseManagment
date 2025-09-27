package com.course.onlinecoursemanagement.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ApiException extends RuntimeException {
    private String message;
    private int Status;

    public ApiException(String message) {
        super(message);
    }
}
