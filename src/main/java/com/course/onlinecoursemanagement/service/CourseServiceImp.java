package com.course.onlinecoursemanagement.service;


import com.course.onlinecoursemanagement.model.Course;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.response.CourseResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseServiceImp implements CourseService {
    @Override
    public CourseResponseDTO getAddCourse(Course course, User instructor) {
        return null;
    }
}
