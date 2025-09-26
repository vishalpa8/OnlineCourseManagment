package com.course.onlinecoursemanagement.Response;

import com.course.onlinecoursemanagement.Model.Course;
import com.course.onlinecoursemanagement.Model.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long userId;
    private String name;
    private String email;
    private RoleType userRole;
    private List<Course> courses;
}
