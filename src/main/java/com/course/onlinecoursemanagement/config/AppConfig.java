package com.course.onlinecoursemanagement.config;


import com.course.onlinecoursemanagement.model.Role;
import com.course.onlinecoursemanagement.model.RoleType;
import com.course.onlinecoursemanagement.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository){
        return args -> {
            roleRepository.findByRoleType(RoleType.ADMIN).orElseGet(() -> {
                Role admin_role = new Role(RoleType.ADMIN);
                roleRepository.save(admin_role);
                return admin_role;
            });
            roleRepository.findByRoleType(RoleType.STUDENT).orElseGet(() -> {
                Role user_role = new Role(RoleType.STUDENT);
                roleRepository.save(user_role);
                return user_role;
            });

            roleRepository.findByRoleType(RoleType.INSTRUCTOR).orElseGet(() -> {
                Role instructor_role = new Role(RoleType.INSTRUCTOR);
                roleRepository.save(instructor_role);
                return instructor_role;
            });
        };
    }
}
