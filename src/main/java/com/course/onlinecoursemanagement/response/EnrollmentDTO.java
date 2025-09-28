package com.course.onlinecoursemanagement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnrollmentDTO {
    private Long enrollmentId;
    private Date enrolledAt;
    private String enrollmentStatus;
    private String payment;
    private Long studentId;
    private String studentName;
    private String studentEmail;
    private Long courseId;
    private String instructorName;
}
