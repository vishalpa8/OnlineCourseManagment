package com.course.onlinecoursemanagement.request;

import com.course.onlinecoursemanagement.model.Course;
import com.course.onlinecoursemanagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnrollmentRequest {
    private User student;
    private Course course;
}
