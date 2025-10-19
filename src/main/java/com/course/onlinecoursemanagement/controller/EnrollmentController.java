package com.course.onlinecoursemanagement.controller;


import com.course.onlinecoursemanagement.request.EnrollmentRequest;
import com.course.onlinecoursemanagement.response.EnrollmentDTO;
import com.course.onlinecoursemanagement.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/users/enrollments/{id}")
    public ResponseEntity<?> getEnrollmentById(@PathVariable Long id) {
        EnrollmentDTO enrollmentDTO = enrollmentService.getEnrollmentUserById(id);
        return new ResponseEntity<>(enrollmentDTO, HttpStatus.OK);
    }

    @GetMapping("/users/enrollments/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<?> getAllEnrollment() {
        List<EnrollmentDTO> enrollmentDTO = enrollmentService.getAllEnrollments();
        return new ResponseEntity<>(enrollmentDTO, HttpStatus.OK);
    }
}
