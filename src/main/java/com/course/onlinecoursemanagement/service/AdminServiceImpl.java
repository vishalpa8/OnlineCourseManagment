package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.exception.ResourceNotFoundException;
import com.course.onlinecoursemanagement.model.Course;
import com.course.onlinecoursemanagement.model.Enrollment;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.repository.CourseRepository;
import com.course.onlinecoursemanagement.repository.UserRepository;
import com.course.onlinecoursemanagement.request.AdminRevenue;
import com.course.onlinecoursemanagement.response.AdminResponseDTO;
import com.course.onlinecoursemanagement.response.AdminRevenueDTO;
import com.course.onlinecoursemanagement.response.StudentCourseHistoryDTO;
import com.course.onlinecoursemanagement.response.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.course.onlinecoursemanagement.config.Utilities.formatDate;
import static com.course.onlinecoursemanagement.service.CourseServiceImp.getCourseResponseDTO;

@Service
public class AdminServiceImpl implements AdminService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public AdminServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public AdminResponseDTO getAllStudentsEnrolledData(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Please enter valid courseId: " + courseId));
        Set<User> students = course.getStudents();
        AdminResponseDTO adminResponseDTO = new AdminResponseDTO();
        adminResponseDTO.setCourse(getCourseResponseDTO(course));
        Set<UserResponseDTO> enrolledUser = students.stream().map(val -> modelMapper.map(val, UserResponseDTO.class)).collect(Collectors.toSet());
        adminResponseDTO.setEnrolledStudents(enrolledUser);
        return adminResponseDTO;
    }

    @Override
    public AdminRevenueDTO getTotalRevenuePerCourseByUser() {
        List<Course> allCourses = courseRepository.findAll();
        AdminRevenueDTO adminRevenueDTO = new AdminRevenueDTO();
        List<AdminRevenue> courseRevenue = allCourses.stream().map(course -> {
            AdminRevenue adminRevenue = new AdminRevenue();
            Long count = (long) course.getStudents().size();
            Double revenue = course.getPrice() * count;
            adminRevenue.setTotalRevenue(revenue);
            adminRevenue.setEnrolledStudents(count);
            adminRevenue.setCourse_id(course.getCourse_id());
            adminRevenue.setDescription(course.getDescription());
            adminRevenue.setInstructor(course.getInstructor().getName());
            adminRevenue.setPrice(course.getPrice());
            adminRevenue.setTitle(course.getTitle());
            return adminRevenue;
        }).toList();
        adminRevenueDTO.setCoursesRevenue(courseRevenue);
        return adminRevenueDTO;
    }

    @Override
    public List<StudentCourseHistoryDTO> getStudentCourseHistoryById(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Please enter valid courseId: " + studentId));

        Set<Course> studentCourses = student.getCourses();

        return studentCourses.stream().map(course -> {
            StudentCourseHistoryDTO studentCourseHistoryDTO = new StudentCourseHistoryDTO();
            studentCourseHistoryDTO.setCourseId(course.getCourse_id());
            studentCourseHistoryDTO.setAmountPaid(course.getPrice());
            studentCourseHistoryDTO.setCourseTitle(course.getTitle());
            Enrollment enrollment = course.getEnrollments().stream()
                    .filter(e -> e.getStudent().getUserId().equals(studentId))
                    .findFirst()
                    .orElse(null);

            if (enrollment != null) {
                studentCourseHistoryDTO.setEnrolledAt(formatDate(enrollment.getEnrolledAt()));
                studentCourseHistoryDTO.setStatus(String.valueOf(enrollment.getEnrollmentStatus()));
            }
            return studentCourseHistoryDTO;
        }).toList();
    }
}
