package com.course.onlinecoursemanagement.response;

import com.course.onlinecoursemanagement.model.Course;
import com.course.onlinecoursemanagement.model.Role;
import com.course.onlinecoursemanagement.model.RoleType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long userId;
    private String name;
    private String email;
    @Setter(AccessLevel.NONE)
    private Set<RoleType> roles;
    private List<Course> courses = new ArrayList<>();

    public void setRoles(Set<Role> roleTypes) {
        roles = roleTypes.stream().map(Role::getRoleType).collect(Collectors.toSet());
    }
}
