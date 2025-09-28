package com.course.onlinecoursemanagement.controller;

import com.course.onlinecoursemanagement.request.PaymentRequest;
import com.course.onlinecoursemanagement.response.PaymentDTO;
import com.course.onlinecoursemanagement.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public ResponseEntity<?> getUserPayment(@RequestBody PaymentRequest payment) {
        PaymentDTO paymentDTO = paymentService.letUserMakePayment(payment);
        return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable Long id) {
        PaymentDTO paymentDTO = paymentService.getPaymentDetailsById(id);
        return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
    }

    @GetMapping("/payments/enrollment/{id}")
    public ResponseEntity<?> getPaymentByEnrollmentId(@PathVariable Long id) {
        PaymentDTO paymentDTO = paymentService.getPaymentByEnrollment(id);
        return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
    }
}
