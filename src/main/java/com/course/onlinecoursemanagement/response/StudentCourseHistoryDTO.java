package com.course.onlinecoursemanagement.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseHistoryDTO {
    private Long courseId;
    private String courseTitle;
    private String status;
    private String enrolledAt;
    private Double amountPaid;
}
