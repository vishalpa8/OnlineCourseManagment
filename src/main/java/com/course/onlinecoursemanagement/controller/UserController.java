package com.course.onlinecoursemanagement.controller;

import com.course.onlinecoursemanagement.model.Role;
import com.course.onlinecoursemanagement.model.RoleType;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.repository.UserRepository;
import com.course.onlinecoursemanagement.request.LoginRequest;
import com.course.onlinecoursemanagement.request.SignupRequest;
import com.course.onlinecoursemanagement.response.UserResponseDTO;
import com.course.onlinecoursemanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users/register")
    public ResponseEntity<UserResponseDTO> registerEndPoint(@Valid @RequestBody SignupRequest request) {
        if (userRepository.existsByUsername((request.getUsername()))) {
            throw new RuntimeException("Error: username is already registered!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Error: email is already registered!");
        }
        UserResponseDTO userResponseDTO = userService.getRegisterUser(request);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> LoginUserEndPoint(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<User> user = userService.getUserLogin(loginRequest);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Sorry bro, you are not registered. Please register first!"));
        }

        Set<RoleType> user_roles = user.get().getRoles().stream()
                .map(Role::getRoleType).collect(Collectors.toSet());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to the application bro!");
        response.put("roles", user_roles);
        response.put("username", user.get().getUsername());
        response.put("email", user.get().getEmail());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserId(@PathVariable Long id) {
        Optional<User> userInfo = userService.getUserById(id);

        if (userInfo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Sorry bro, id is not valid. Please try with correct id!"));
        }
        Set<RoleType> user_roles = userInfo.get().getRoles().stream()
                .map(Role::getRoleType).collect(Collectors.toSet());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to the application bro!");
        response.put("roles", user_roles);
        response.put("username", userInfo.get().getUsername());
        response.put("email", userInfo.get().getEmail());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity<?> getHealthCheck() {
        return ResponseEntity.ok("Application is running");
    }
}
