package com.course.onlinecoursemanagement.response;

import com.course.onlinecoursemanagement.model.Role;
import com.course.onlinecoursemanagement.model.RoleType;
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
    @Setter(AccessLevel.NONE)
    private Set<RoleType> roles;

    public void setRoles(Set<Role> roleTypes) {
        roles = roleTypes.stream().map(Role::getRoleType).collect(Collectors.toSet());
    }
}
