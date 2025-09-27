package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.model.Course;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.response.CourseResponseDTO;
import jakarta.validation.Valid;

public interface CourseService {
    CourseResponseDTO getAddCourse(@Valid Course course, User instructor);
}
