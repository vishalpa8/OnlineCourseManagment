package com.course.onlinecoursemanagement.controller;


import com.course.onlinecoursemanagement.response.AdminResponseDTO;
import com.course.onlinecoursemanagement.response.AdminRevenueDTO;
import com.course.onlinecoursemanagement.response.StudentCourseHistoryDTO;
import com.course.onlinecoursemanagement.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/reports/course/{courseId}/students")
    public ResponseEntity<?> getAllStudentsEnrolledInCourse(@PathVariable Long courseId) {
        AdminResponseDTO adminResponseDTO = adminService.getAllStudentsEnrolledData(courseId);
        return new ResponseEntity<>(adminResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/reports/course/revenue")
    public ResponseEntity<?> getTotalRevenuePerCourse() {
        AdminRevenueDTO adminResponseDTO = adminService.getTotalRevenuePerCourseByUser();
        return new ResponseEntity<>(adminResponseDTO, HttpStatus.OK);
    }


    @GetMapping("/reports/student/{studentId}/history")
    public ResponseEntity<?> getStudentCourseHistory(@PathVariable Long studentId) {
        List<StudentCourseHistoryDTO> studentCourseHistoryDTO = adminService.getStudentCourseHistoryById(studentId);
        return new ResponseEntity<>(studentCourseHistoryDTO, HttpStatus.OK);
    }
}
