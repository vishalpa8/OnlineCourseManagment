package com.course.onlinecoursemanagement.controller;

import com.course.onlinecoursemanagement.exception.ApiException;
import com.course.onlinecoursemanagement.exception.ResourceNotFoundException;
import com.course.onlinecoursemanagement.model.Course;
import com.course.onlinecoursemanagement.model.Role;
import com.course.onlinecoursemanagement.model.RoleType;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.repository.UserRepository;
import com.course.onlinecoursemanagement.response.CourseResponseDTO;
import com.course.onlinecoursemanagement.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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

        boolean isInstructor = instructor.getRoles().stream().map(Role::getRoleType).
                anyMatch(role -> Set.of(RoleType.ADMIN, RoleType.INSTRUCTOR).contains(role));

        if (!isInstructor) {
            throw new ApiException("You are not authorized to create course");
        }

        CourseResponseDTO courseResponseDTO = courseService.getAddCourse(course, instructor);
        return new ResponseEntity<>(courseResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/courses/all")
    public ResponseEntity<?> getAllCourses() {
        List<CourseResponseDTO> courseResponseDTO = courseService.getAllCourses();
        return new ResponseEntity<>(courseResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        CourseResponseDTO courseResponseDTO = courseService.getCourseById(id);
        return new ResponseEntity<>(courseResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/courses/delete/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable Long id) {
        CourseResponseDTO courseResponseDTO = courseService.deleteCourseById(id);
        return new ResponseEntity<>(courseResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/courses/update/{id}")
    public ResponseEntity<?> updateCourseDetails(@RequestBody CourseResponseDTO updateCourseRequest, @PathVariable Long id) {
        CourseResponseDTO courseResponseDTO = courseService.updateCourseDetails(updateCourseRequest, id);
        return new ResponseEntity<>(courseResponseDTO, HttpStatus.OK);
    }
}
