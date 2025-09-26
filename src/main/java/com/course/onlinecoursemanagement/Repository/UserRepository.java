package com.course.onlinecoursemanagement.Repository;

import com.course.onlinecoursemanagement.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
