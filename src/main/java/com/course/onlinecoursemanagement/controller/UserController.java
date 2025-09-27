package com.course.onlinecoursemanagement.controller;

import com.course.onlinecoursemanagement.exception.MessageException;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.repository.RoleRepository;
import com.course.onlinecoursemanagement.repository.UserRepository;
import com.course.onlinecoursemanagement.request.SignupRequest;
import com.course.onlinecoursemanagement.response.UserResponseDTO;
import com.course.onlinecoursemanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users/register")
    public ResponseEntity<?> registerEndPoint(@Valid @RequestBody SignupRequest request){
        if (userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body(new MessageException(("Error: email is already registered!")));
        }
        UserResponseDTO userResponseDTO = userService.getRegisterUser(request);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/health")
    public ResponseEntity<?> getHealthCheck(){
        return ResponseEntity.ok("Application is running");
    }
}
