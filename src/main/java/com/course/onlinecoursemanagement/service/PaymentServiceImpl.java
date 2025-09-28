package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.exception.ApiException;
import com.course.onlinecoursemanagement.exception.ResourceNotFoundException;
import com.course.onlinecoursemanagement.model.Enrollment;
import com.course.onlinecoursemanagement.model.Payment;
import com.course.onlinecoursemanagement.model.Status;
import com.course.onlinecoursemanagement.repository.EnrollmentRepository;
import com.course.onlinecoursemanagement.repository.PaymentRepository;
import com.course.onlinecoursemanagement.request.PaymentRequest;
import com.course.onlinecoursemanagement.response.PaymentDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static com.course.onlinecoursemanagement.config.Utilities.formatDate;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, EnrollmentRepository enrollmentRepository) {
        this.paymentRepository = paymentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public PaymentDTO letUserMakePayment(PaymentRequest paymentRequest) {
        Enrollment enrollment = enrollmentRepository.findById(paymentRequest.getEnrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Given enrollmentId is not valid, try again!! " + paymentRequest.getEnrollmentId()));
        Payment payment = paymentRepository.findByEnrollment(enrollment);

        int value = Double.compare(paymentRequest.getAmount(), enrollment.getCourse().getPrice());

        if (value < 0) {
            double require_amount = enrollment.getCourse().getPrice() - paymentRequest.getAmount();
            throw new ApiException("you are not paying the right amount!, require more money " + require_amount);
        }

        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentStatus(Status.COMPLETED);
        enrollment.setEnrollmentStatus(Status.ACTIVE);
        payment.setEnrollment(enrollment);
        paymentRepository.save(payment);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(paymentRequest.getAmount());
        paymentDTO.setPaymentStatus(String.valueOf(Status.COMPLETED));
        paymentDTO.setPaymentDate(formatDate(payment.getPaymentDate()));
        paymentDTO.setPaymentId(payment.getPaymentId());
        return paymentDTO;
    }

    @Override
    public PaymentDTO getPaymentDetailsById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Please enter valid id: " + id));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setPaymentStatus(String.valueOf(payment.getPaymentStatus()));
        paymentDTO.setPaymentDate(formatDate(payment.getPaymentDate()));
        paymentDTO.setPaymentId(payment.getPaymentId());
        return paymentDTO;
    }

    @Override
    public PaymentDTO getPaymentByEnrollment(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Please enter valid id: " + id));
        Payment payment = enrollment.getPayment();
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setPaymentStatus(String.valueOf(payment.getPaymentStatus()));
        paymentDTO.setPaymentDate(formatDate(payment.getPaymentDate()));
        paymentDTO.setPaymentId(payment.getPaymentId());
        return paymentDTO;
    }

}
