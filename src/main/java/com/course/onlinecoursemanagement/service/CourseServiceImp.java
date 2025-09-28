package com.course.onlinecoursemanagement.service;


import com.course.onlinecoursemanagement.exception.ApiException;
import com.course.onlinecoursemanagement.exception.ResourceNotFoundException;
import com.course.onlinecoursemanagement.model.Course;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.repository.CourseRepository;
import com.course.onlinecoursemanagement.response.CourseResponseDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.course.onlinecoursemanagement.config.Utilities.hasValue;

@Service
@AllArgsConstructor
public class CourseServiceImp implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public CourseResponseDTO getAddCourse(Course course, User instructor) {

        if (courseRepository.existsByTitle(course.getTitle())) {
            throw new ApiException("Course is already registered, add new!");
        }

        course.setInstructor(instructor);
        instructor.getCoursesTaught().add(course);
        courseRepository.save(course);
        return getCourseResponseDTO(course);
    }

    @Override
    public List<CourseResponseDTO> getAllCourses() {
        List<Course> course = courseRepository.findAll();
        return course.stream().map(CourseServiceImp::getCourseResponseDTO).toList();
    }

    @Override
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Please enter valid id " + id));
        return getCourseResponseDTO(course);
    }

    @Override
    public CourseResponseDTO deleteCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Please enter valid id " + id));
        courseRepository.delete(course);
        return getCourseResponseDTO(course);
    }

    @Override
    @Transactional
    public CourseResponseDTO updateCourseDetails(CourseResponseDTO updateCourseRequest, Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Please enter valid id " + id));

        String description = updateCourseRequest.getDescription();
        String title = updateCourseRequest.getTitle();
        Double price = updateCourseRequest.getPrice();
        boolean update = false;

        if (hasValue(description) && !description.equals(course.getDescription())) {
            course.setDescription(description);
            update = true;
        }

        if (hasValue(title) && !title.equals(course.getTitle())) {
            course.setTitle(title);
            update = true;
        }

        if (price != null && !price.equals(course.getPrice())) {
            course.setPrice(price);
            update = true;
        }
        if (update) courseRepository.save(course);
        return getCourseResponseDTO(course);
    }

    public static CourseResponseDTO getCourseResponseDTO(Course course) {
        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
        courseResponseDTO.setCourse_id(course.getCourse_id());
        courseResponseDTO.setInstructor(course.getInstructor().getName());
        courseResponseDTO.setDescription(course.getDescription());
        courseResponseDTO.setPrice(course.getPrice());
        courseResponseDTO.setTitle(course.getTitle());
        return courseResponseDTO;
    }
}
