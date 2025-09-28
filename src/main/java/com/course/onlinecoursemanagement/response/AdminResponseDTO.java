package com.course.onlinecoursemanagement.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminResponseDTO {
    private CourseResponseDTO course;
    private Set<UserResponseDTO> enrolledStudents = new HashSet<>();
}
