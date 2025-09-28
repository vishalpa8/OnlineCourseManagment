package com.course.onlinecoursemanagement.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Double amount;
    private Long enrollmentId;

}
