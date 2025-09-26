package com.course.onlinecoursemanagement.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @Positive
    private Double amount;

    @CreationTimestamp
    @Column(updatable = false)
    private Date paymentDate;

    @NotBlank
    private Status paymentStatus;

    @OneToOne()
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;
}
