package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.exception.ApiException;
import com.course.onlinecoursemanagement.exception.ResourceNotFoundException;
import com.course.onlinecoursemanagement.model.*;
import com.course.onlinecoursemanagement.repository.CourseRepository;
import com.course.onlinecoursemanagement.repository.EnrollmentRepository;
import com.course.onlinecoursemanagement.repository.UserRepository;
import com.course.onlinecoursemanagement.request.EnrollmentRequest;
import com.course.onlinecoursemanagement.response.EnrollmentDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.course.onlinecoursemanagement.config.Utilities.hasValue;

@Service
@AllArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public EnrollmentDTO letUserEnrolled(EnrollmentRequest enrollmentRequest) {
        User userInfo = enrollmentRequest.getStudent();
        Course courseInfo = enrollmentRequest.getCourse();

        User userFromDB = userRepository.findById(userInfo.getUserId()).
                or(() -> userRepository.findByUsername(userInfo.getUsername())).
                orElseThrow(() -> new ApiException("User is not registered"));

        boolean isValidUser = userInfo.getEmail().equals(userFromDB.getEmail());

        if (!isValidUser) {
            throw new ApiException("User is not registered!");
        }
        Course courseFromDB = courseRepository.findById(courseInfo.getCourse_id()).
                orElseThrow(() -> new ApiException("Course is not found"));


        boolean isCourseContent = !courseFromDB.getTitle().equalsIgnoreCase(courseInfo.getTitle()) || !courseFromDB.getDescription()
                .equalsIgnoreCase(courseInfo.getDescription()) || Double.compare(courseFromDB.getPrice(), courseInfo.getPrice()) != 0;

        if (isCourseContent)
            throw new ApiException("Course contains are not correct!");

        boolean alreadyRegistered = courseFromDB.getStudents().stream()
                .anyMatch(u -> u.getUserId().equals(userFromDB.getUserId()));
        if (alreadyRegistered) {
            throw new ApiException("User is already enrolled in this course");
        }

        courseFromDB.getStudents().add(userFromDB);
        courseRepository.save(courseFromDB);

        Enrollment enrollment = modelMapper.map(enrollmentRequest, Enrollment.class);
        Payment payment = new Payment(Status.PENDING, enrollment);
        enrollment.setPayment(payment);
        enrollment.setEnrollmentStatus(Status.PENDING);
        enrollment.setCourse(courseFromDB);
        enrollment.setStudent(userFromDB);
        enrollmentRepository.save(enrollment);

        return getEnrollmentDTO(enrollment);
    }

    @Override
    public EnrollmentDTO getEnrollmentUserById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Given enrollmentId is not valid!!"));
        return getEnrollmentDTO(enrollment);
    }

    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        List<Enrollment> enrollment = enrollmentRepository.findAll();
        return enrollment.stream().map(EnrollmentServiceImpl::getEnrollmentDTO).toList();
    }

    public static EnrollmentDTO getEnrollmentDTO(Enrollment enrollment) {
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setCourseId(enrollment.getCourse().getCourse_id());
        enrollmentDTO.setEnrollmentId(enrollment.getEnrollmentId());
        enrollmentDTO.setStudentId(enrollment.getStudent().getUserId());
        enrollmentDTO.setInstructorName(enrollment.getCourse().getInstructor().getName());

        if (!hasValue(String.valueOf(enrollment.getPayment().getPaymentStatus()))) {
            enrollmentDTO.setPayment(String.valueOf(Status.PENDING));
        } else {
            enrollmentDTO.setPayment(String.valueOf(enrollment.getPayment().getPaymentStatus()));
        }
        if (!hasValue(String.valueOf(enrollment.getEnrollmentStatus()))) {
            enrollmentDTO.setEnrollmentStatus(String.valueOf(Status.PENDING));
        } else {
            enrollmentDTO.setEnrollmentStatus(String.valueOf(enrollment.getEnrollmentStatus()));
        }
        enrollmentDTO.setStudentName(enrollment.getStudent().getName());
        enrollmentDTO.setStudentEmail(enrollment.getStudent().getEmail());
        String formattedTime = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH)
                .format(enrollment.getEnrolledAt());
        enrollmentDTO.setEnrolledAt(formattedTime);
        return enrollmentDTO;
    }

}
