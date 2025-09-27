package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.exception.ApiException;
import com.course.onlinecoursemanagement.exception.ResourceNotFoundException;
import com.course.onlinecoursemanagement.model.Role;
import com.course.onlinecoursemanagement.model.RoleType;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.repository.RoleRepository;
import com.course.onlinecoursemanagement.repository.UserRepository;
import com.course.onlinecoursemanagement.request.LoginRequest;
import com.course.onlinecoursemanagement.request.SignupRequest;
import com.course.onlinecoursemanagement.request.UpdateRequest;
import com.course.onlinecoursemanagement.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
                    .orElseThrow(() -> new ResourceNotFoundException("Error: User Role not found"));
            roles.add(user_role);
        } else {
            strRoles.forEach(role -> {
                        switch (role.toLowerCase()) {
                            case "admin":
                                Role admin_role = roleRepository.findByRoleType(RoleType.ADMIN)
                                        .orElseThrow(() -> new ResourceNotFoundException("Error: Admin Role not found"));
                                roles.add(admin_role);
                                break;
                            case "instructor":
                                Role instruct_role = roleRepository.findByRoleType(RoleType.INSTRUCTOR)
                                        .orElseThrow(() -> new ResourceNotFoundException("Error: Instructor Role not found"));
                                roles.add(instruct_role);
                                break;
                            default:
                                throw new ResourceNotFoundException(role, "roleType", "roles");
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
    public List<UserResponseDTO> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream().map(val -> modelMapper.map(val, UserResponseDTO.class)).toList();
    }

    @Override
    public UserResponseDTO getUpdateDetails(UpdateRequest updateRequest, Long id) {
        User userInfo = userRepository.findById(id).orElseThrow(() -> new ApiException("Please enter valid id!"));

        if (hasValue(updateRequest.getEmail()) && !updateRequest.getEmail().equals(userInfo.getEmail())) {
            userInfo.setEmail(updateRequest.getEmail());
        }

        if (hasValue(updateRequest.getName()) && !updateRequest.getName().equals(userInfo.getName())) {
            userInfo.setName(updateRequest.getName());
        }

        if (hasValue(updateRequest.getUsername()) && !updateRequest.getUsername().equals(userInfo.getUsername())) {
            userInfo.setUsername(updateRequest.getUsername());
        }

        if (updateRequest.getRoles() != null && !updateRequest.getRoles().isEmpty()) {
            Set<Role> updatedRoles = updateRequest.getRoles().stream().
                    map(val -> roleRepository.findByRoleType(RoleType.valueOf(val.toUpperCase())).orElseThrow(
                            () -> new RuntimeException("Role not found: " + val)
                    )).collect(Collectors.toSet());

            if (!updatedRoles.isEmpty()) {
                Set<Role> mergedRoles = new HashSet<>(userInfo.getRoles());
                mergedRoles.addAll(updatedRoles);
                userInfo.setRoles(mergedRoles);
            }
        }

        userRepository.save(userInfo);
        return modelMapper.map(userInfo, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO getDeleteDetails(Long id) {
        User userInfo = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id is not valid: " + id));
        userRepository.delete(userInfo);
        return modelMapper.map(userInfo, UserResponseDTO.class);
    }


    private boolean hasValue(String str) {
        return str != null && !str.isBlank();
    }


}
