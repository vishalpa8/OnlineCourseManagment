package com.course.onlinecoursemanagement.controller;

import com.course.onlinecoursemanagement.exception.ApiException;
import com.course.onlinecoursemanagement.exception.ResourceNotFoundException;
import com.course.onlinecoursemanagement.model.Course;
import com.course.onlinecoursemanagement.model.RoleType;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.repository.UserRepository;
import com.course.onlinecoursemanagement.response.CourseResponseDTO;
import com.course.onlinecoursemanagement.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CourseController {

    private final UserRepository userRepository;
    private final CourseService courseService;

    public CourseController(UserRepository userRepository, CourseService courseService) {
        this.userRepository = userRepository;
        this.courseService = courseService;
    }

    @PostMapping("/courses/{id}")
    public ResponseEntity<?> createCourses(@Valid @RequestBody Course course, @PathVariable Long id) {
        User instructor = userRepository.findById(id).orElseThrow
                (() -> new ResourceNotFoundException("Please enter valid id"));

        boolean isInstructor = instructor.getRoles().stream().anyMatch(val -> val.getRoleType().equals(RoleType.INSTRUCTOR));

        if (!isInstructor) {
            throw new ApiException("You are not authorized to create course");
        }

        CourseResponseDTO courseResponseDTO = courseService.getAddCourse(course, instructor);
        return new ResponseEntity<>(courseResponseDTO, HttpStatus.CREATED);
    }
}
