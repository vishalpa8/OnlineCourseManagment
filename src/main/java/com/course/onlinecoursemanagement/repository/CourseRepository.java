package com.course.onlinecoursemanagement.repository;

import com.course.onlinecoursemanagement.model.Course;
import com.course.onlinecoursemanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByTitle(String title);

    List<Course> findByStudents(User student);
}
