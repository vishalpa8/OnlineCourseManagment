package com.course.onlinecoursemanagement.Controller;

import com.course.onlinecoursemanagement.Model.User;
import com.course.onlinecoursemanagement.Response.UserResponseDTO;
import com.course.onlinecoursemanagement.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserResponseDTO> registerEndPoint(@Valid @RequestBody User user){
        UserResponseDTO userResponseDTO = userService.getRegisterUser(user);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/health")
    public ResponseEntity<?> getHealthCheck(){
        return ResponseEntity.ok("Application is running");
    }
}
