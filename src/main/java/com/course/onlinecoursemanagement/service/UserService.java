package com.course.onlinecoursemanagement.service;

import com.course.onlinecoursemanagement.request.SignupRequest;
import com.course.onlinecoursemanagement.response.UserResponseDTO;

public interface UserService {
    UserResponseDTO getRegisterUser(SignupRequest signupRequest);

}
