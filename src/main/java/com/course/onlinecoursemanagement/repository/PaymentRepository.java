package com.course.onlinecoursemanagement.repository;

import com.course.onlinecoursemanagement.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
