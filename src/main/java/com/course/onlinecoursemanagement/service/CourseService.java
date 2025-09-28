package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.model.Course;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.response.CourseResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface CourseService {
    CourseResponseDTO getAddCourse(@Valid Course course, User instructor);

    List<CourseResponseDTO> getAllCourses();

    CourseResponseDTO getCourseById(Long id);

    CourseResponseDTO deleteCourseById(Long id);

    CourseResponseDTO updateCourseDetails(CourseResponseDTO updateCourseRequest, Long id);
}