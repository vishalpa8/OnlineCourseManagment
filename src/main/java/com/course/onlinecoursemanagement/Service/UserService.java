package com.course.onlinecoursemanagement.Service;

import com.course.onlinecoursemanagement.Model.User;
import com.course.onlinecoursemanagement.Response.UserResponseDTO;

public interface UserService {
    UserResponseDTO getRegisterUser(User user);

}
