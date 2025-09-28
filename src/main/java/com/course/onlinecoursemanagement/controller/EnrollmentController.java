package com.course.onlinecoursemanagement.controller;


import com.course.onlinecoursemanagement.request.EnrollmentRequest;
import com.course.onlinecoursemanagement.response.EnrollmentDTO;
import com.course.onlinecoursemanagement.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/users/enrollments")
    public ResponseEntity<?> makeEnrollment(@RequestBody EnrollmentRequest enrollmentRequest) {
        EnrollmentDTO enrollmentDTO1 = enrollmentService.letUserEnrolled(enrollmentRequest);
        return new ResponseEntity<>(enrollmentDTO1, HttpStatus.CREATED);
    }
}
