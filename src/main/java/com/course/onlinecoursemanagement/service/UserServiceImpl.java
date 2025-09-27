package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.model.Role;
import com.course.onlinecoursemanagement.model.RoleType;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.repository.RoleRepository;
import com.course.onlinecoursemanagement.repository.UserRepository;
import com.course.onlinecoursemanagement.request.LoginRequest;
import com.course.onlinecoursemanagement.request.SignupRequest;
import com.course.onlinecoursemanagement.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public UserResponseDTO getRegisterUser(SignupRequest signupRequest) {
        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        User userInfo = new User();

        userInfo.setEmail(signupRequest.getEmail());
        userInfo.setName(signupRequest.getName());
        userInfo.setUsername(signupRequest.getUsername());

        if (strRoles == null) {
            Role user_role = roleRepository.findByRoleType(RoleType.STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: User Role not found"));
            roles.add(user_role);
        } else {
            strRoles.forEach(role -> {
                        switch (role.toLowerCase()) {
                            case "admin":
                                Role admin_role = roleRepository.findByRoleType(RoleType.ADMIN)
                                        .orElseThrow(() -> new RuntimeException("Error: Admin Role not found"));
                                roles.add(admin_role);
                                break;
                            case "instructor":
                                Role instruct_role = roleRepository.findByRoleType(RoleType.INSTRUCTOR)
                                        .orElseThrow(() -> new RuntimeException("Error: Instructor Role not found"));
                                roles.add(instruct_role);
                                break;
                            case "student":
                                Role user_role = roleRepository.findByRoleType(RoleType.STUDENT)
                                        .orElseThrow(() -> new RuntimeException("Error: User Role not found"));
                                roles.add(user_role);
                        }
                    }
            );
        }

        userInfo.setRoles(roles);
        userRepository.save(userInfo);
        return modelMapper.map(userInfo, UserResponseDTO.class);
    }

    @Override
    public Optional<User> getUserLogin(LoginRequest loginRequest) {
        return userRepository.findByEmail(loginRequest.getEmail())
                .or(() -> userRepository.findByUsername(loginRequest.getUsername()));
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
