package com.course.onlinecoursemanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @NotNull
    private Double amount;

    @CreationTimestamp
    @Column(updatable = false)
    private Date paymentDate;

    @NotNull(message = "status is required")
    @Enumerated(EnumType.STRING)
    private Status paymentStatus;

    public Payment(Status paymentStatus, Enrollment enrollment) {
        this.paymentStatus = paymentStatus;
        this.enrollment = enrollment;
        amount = 0.0;
    }

    @OneToOne()
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;
}
