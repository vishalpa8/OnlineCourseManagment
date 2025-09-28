package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.request.EnrollmentRequest;
import com.course.onlinecoursemanagement.response.EnrollmentDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO letUserEnrolled(EnrollmentRequest enrollmentRequest);

    EnrollmentDTO getEnrollmentUserById(Long id);

    List<EnrollmentDTO> getAllEnrollments();
}