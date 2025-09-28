package com.course.onlinecoursemanagement.repository;

import com.course.onlinecoursemanagement.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
