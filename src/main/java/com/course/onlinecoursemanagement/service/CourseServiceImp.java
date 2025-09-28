package com.course.onlinecoursemanagement.service;


import com.course.onlinecoursemanagement.exception.ResourceNotFoundException;
import com.course.onlinecoursemanagement.model.Course;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.repository.CourseRepository;
import com.course.onlinecoursemanagement.repository.UserRepository;
import com.course.onlinecoursemanagement.response.CourseResponseDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.course.onlinecoursemanagement.config.Utilities.hasValue;

@Service
@AllArgsConstructor
public class CourseServiceImp implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public CourseResponseDTO getAddCourse(Course course, User instructor) {
        course.setInstructor(instructor);
        instructor.getCoursesTaught().add(course);
        userRepository.save(instructor);
        return modelMapper.map(course, CourseResponseDTO.class);
    }

    @Override
    public List<CourseResponseDTO> getAllCourses() {
        List<Course> course = courseRepository.findAll();
        return course.stream().map(val -> modelMapper.map(val, CourseResponseDTO.class)).toList();
    }

    @Override
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Please enter valid id " + id));
        return modelMapper.map(course, CourseResponseDTO.class);
    }

    @Override
    public CourseResponseDTO deleteCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Please enter valid id " + id));
        CourseResponseDTO courseResponseDTO = modelMapper.map(course, CourseResponseDTO.class);
        courseRepository.delete(course);
        return courseResponseDTO;
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
        return modelMapper.map(course, CourseResponseDTO.class);
    }
}
