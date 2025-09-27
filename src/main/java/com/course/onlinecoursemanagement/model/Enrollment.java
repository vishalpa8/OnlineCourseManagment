package com.course.onlinecoursemanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @CreationTimestamp
    @Column(updatable = false)
    private Date enrolledAt;

    @NotBlank
    private Status enrollmentStatus;

    @OneToOne(mappedBy = "enrollment",cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;

    @ManyToOne()
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User student;

}
