package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.request.LoginRequest;
import com.course.onlinecoursemanagement.request.SignupRequest;
import com.course.onlinecoursemanagement.request.UpdateUserRequest;
import com.course.onlinecoursemanagement.response.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponseDTO createUser(SignupRequest signupRequest);

    Optional<User> getUserLogin(LoginRequest loginRequest);

    Optional<User> getUserById(Long id);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUserDetailsById(UpdateUserRequest updateRequest, Long id);

    UserResponseDTO deleteUserById(Long id);
}

