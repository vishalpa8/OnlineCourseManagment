package com.course.onlinecoursemanagement.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDTO {
    private Long course_id;
    private String title;
    private String description;
    private Double price;
    private String instructor;
}
