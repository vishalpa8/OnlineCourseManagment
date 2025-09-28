package com.course.onlinecoursemanagement.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

    @GetMapping("/reports/course/{courseId}/students")
    public ResponseEntity<?> getAllStudentsEnrolledInCourse(@PathVariable String courseId) {
        return null;
    }

    @GetMapping("/reports/course/revenue")
    public ResponseEntity<?> getTotalRevenuePerCourse() {
        return null;
    }


    @GetMapping("/reports/student/{studentId}/history")
    public ResponseEntity<?> getTotalRevenuePerCourse(@PathVariable String studentId) {
        return null;
    }
}
