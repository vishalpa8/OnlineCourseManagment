package com.course.onlinecoursemanagement.response;

import com.course.onlinecoursemanagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDTO {
    private String title;
    private String description;
    private Double price;
    private Set<User> students;
    private UserResponseDTO instructor;
}
