package com.course.onlinecoursemanagement.response;


import com.course.onlinecoursemanagement.request.AdminRevenue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminRevenueDTO {
    List<AdminRevenue> coursesRevenue = new ArrayList<>();
}
