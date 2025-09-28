package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.response.AdminResponseDTO;
import com.course.onlinecoursemanagement.response.AdminRevenueDTO;
import com.course.onlinecoursemanagement.response.StudentCourseHistoryDTO;

import java.util.List;

public interface AdminService {
    AdminResponseDTO getAllStudentsEnrolledData(Long courseId);

    AdminRevenueDTO getTotalRevenuePerCourseByUser();

    List<StudentCourseHistoryDTO> getStudentCourseHistoryById(Long studentId);
}
