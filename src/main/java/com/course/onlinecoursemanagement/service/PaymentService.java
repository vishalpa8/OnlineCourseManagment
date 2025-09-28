package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.request.PaymentRequest;
import com.course.onlinecoursemanagement.response.PaymentDTO;

public interface PaymentService {
    PaymentDTO letUserMakePayment(PaymentRequest payment);

    PaymentDTO getPaymentDetailsById(Long id);

    PaymentDTO getPaymentByEnrollment(Long id);
}