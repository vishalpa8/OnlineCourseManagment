package com.course.onlinecoursemanagement.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminRevenue {
    private Long course_id;
    private String title;
    private String description;
    private Double price;
    private String instructor;
    private Double totalRevenue;
    private Long enrolledStudents;
}
