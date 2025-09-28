package com.course.onlinecoursemanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Objects;

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

    @NotNull(message = "status is required")
    @Enumerated(EnumType.STRING)
    private Status enrollmentStatus;

    @OneToOne(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;

    @ManyToOne()
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User student;

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId); // ✅ Only ID
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Enrollment other)) return false;
        return Objects.equals(this.enrollmentId, other.enrollmentId);
    }
}
