package com.course.onlinecoursemanagement.config;


import com.course.onlinecoursemanagement.model.Role;
import com.course.onlinecoursemanagement.model.RoleType;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.repository.RoleRepository;
import com.course.onlinecoursemanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role admin = roleRepository.findByRoleType(RoleType.ROLE_ADMIN).orElseGet(() -> {
                Role admin_role = new Role(RoleType.ROLE_ADMIN);
                roleRepository.save(admin_role);
                return admin_role;
            });
            Role user = roleRepository.findByRoleType(RoleType.ROLE_STUDENT).orElseGet(() -> {
                Role user_role = new Role(RoleType.ROLE_STUDENT);
                roleRepository.save(user_role);
                return user_role;
            });

            Role instructor = roleRepository.findByRoleType(RoleType.ROLE_INSTRUCTOR).orElseGet(() -> {
                Role instructor_role = new Role(RoleType.ROLE_INSTRUCTOR);
                roleRepository.save(instructor_role);
                return instructor_role;
            });

            Set<Role> userRoles = Set.of(user);
            Set<Role> instructorRoles = Set.of(user, instructor);
            Set<Role> adminRoles = Set.of(user, instructor, admin);

            if (!userRepository.existsByUsername("user1")) {
                User user1 = new User("user1", "user1", "user1@gmail.com",passwordEncoder.encode("user@123"));
                userRepository.save(user1);
            }

            if (!userRepository.existsByUsername("teacher1")) {
                User teacher = new User("teacher1", "teacher1", "teacher12@gmail.com",passwordEncoder.encode("teacher@123"));
                userRepository.save(teacher);
            }

            if (!userRepository.existsByUsername("admin")) {
                User admin_1 = new User("admin", "admin", "admin12@gmail.com",passwordEncoder.encode("admin@123"));
                userRepository.save(admin_1);
            }

            userRepository.findByUsername("user1").ifPresent(a -> {
                a.setRoles(userRoles);
                userRepository.save(a);
            });

            userRepository.findByUsername("teacher1").ifPresent(a -> {
                a.setRoles(instructorRoles);
                userRepository.save(a);
            });

            userRepository.findByUsername("admin").ifPresent(a -> {
                a.setRoles(adminRoles);
                userRepository.save(a);
            });

        };
    }
}
