package com.course.onlinecoursemanagement.response;

import com.course.onlinecoursemanagement.model.Role;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long userId;
    private String name;
    private String username;
    private String email;
    private String jwtToken;

    @Setter(AccessLevel.NONE)
    private Set<String> roles;

    public void setRoles(Set<Role> roleTypes) {
        roles = roleTypes.stream().map(role -> role.getRoleType().name()).collect(Collectors.toSet());
    }

    public UserResponseDTO(Long userId, String username, String email, Set<String> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
