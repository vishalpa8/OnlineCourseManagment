package com.course.onlinecoursemanagement.controller;

import com.course.onlinecoursemanagement.exception.ApiException;
import com.course.onlinecoursemanagement.model.Role;
import com.course.onlinecoursemanagement.model.RoleType;
import com.course.onlinecoursemanagement.model.User;
import com.course.onlinecoursemanagement.repository.UserRepository;
import com.course.onlinecoursemanagement.request.LoginRequest;
import com.course.onlinecoursemanagement.request.SignupRequest;
import com.course.onlinecoursemanagement.request.UpdateUserRequest;
import com.course.onlinecoursemanagement.response.UserResponseDTO;
import com.course.onlinecoursemanagement.security.JwtConfig.JwtUtils;
import com.course.onlinecoursemanagement.security.service.UserDetailsImpl;
import com.course.onlinecoursemanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AuthController {


    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    @PostMapping("/auth/register")
    public ResponseEntity<?> registerEndPoint(@Valid @RequestBody SignupRequest request) {
        if (userRepository.existsByUsername((request.getUsername()))) {
            return ResponseEntity.badRequest().body("Error: Username is already token!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already token!");
        }
        UserResponseDTO userResponseDTO = userService.createUser(request);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> LoginUserEndPoint(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            Map<String, Object>map = new HashMap<>();
            map.put("message","Bad credentials");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        Set<String>roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        UserResponseDTO response = new UserResponseDTO(userDetails.getUserId(), userDetails.getName(), userDetails.getUsername(), userDetails.getEmail(), jwtCookie.toString(), roles);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR','USER')")
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

    @GetMapping("/users/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR','USER')")
    public ResponseEntity<?> getAllUsers() {
        List<UserResponseDTO> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PutMapping("/users/update/{id}")
    public ResponseEntity<?> updateUserData(@RequestBody UpdateUserRequest updateRequest, @PathVariable Long id) {
        UserResponseDTO userInfo = userService.updateUserDetailsById(updateRequest, id);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUserData(@PathVariable Long id) {
        UserResponseDTO userInfo = userService.deleteUserById(id);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @GetMapping("/auth/username")
    public String currentUsername(Authentication authentication) {
        if (authentication != null) {
            return authentication.getName();
        } else {
            return "";
        }
    }

    @GetMapping("/auth/user")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        if (authentication == null) {
            return new ResponseEntity<>(new ApiException("you are not authorized to use!"), HttpStatus.UNAUTHORIZED);
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        UserResponseDTO response = new UserResponseDTO(userDetails.getUserId(), userDetails.getUsername(), userDetails.getEmail(), roles);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/auth/signout")
    public ResponseEntity<?> signOutUser(Authentication authentication) {
        if (authentication == null) {
            return new ResponseEntity<>(new ApiException("you are already signed out!"), HttpStatus.OK);
        }
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                cookie.toString()).body("You've signed out!");
    }

    @GetMapping("/health")
    public ResponseEntity<?> getHealthCheck() {
        return ResponseEntity.ok("Application is running");
    }
}
