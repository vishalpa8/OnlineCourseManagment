package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.request.LoginRequest;
import com.course.onlinecoursemanagement.request.SignupRequest;
import com.course.onlinecoursemanagement.request.UpdateRequest;
import com.course.onlinecoursemanagement.response.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponseDTO getRegisterUser(SignupRequest signupRequest);

    Optional<User> getUserLogin(LoginRequest loginRequest);

    Optional<User> getUserById(Long id);

    List<User> getAllUsers();

    UserResponseDTO getUpdateDetails(UpdateRequest updateRequest, Long id);

    UserResponseDTO getDeleteDetails(Long id);
}

