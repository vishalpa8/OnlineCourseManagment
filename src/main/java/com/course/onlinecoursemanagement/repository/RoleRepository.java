package com.course.onlinecoursemanagement.repository;

import com.course.onlinecoursemanagement.model.Role;
import com.course.onlinecoursemanagement.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
