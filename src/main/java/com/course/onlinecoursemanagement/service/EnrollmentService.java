package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.request.EnrollmentRequest;
import com.course.onlinecoursemanagement.response.EnrollmentDTO;

public interface EnrollmentService {
    EnrollmentDTO letUserEnrolled(EnrollmentRequest enrollmentRequest);
}