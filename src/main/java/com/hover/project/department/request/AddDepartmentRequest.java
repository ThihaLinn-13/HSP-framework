package com.hover.project.department.request;

import jakarta.validation.constraints.NotBlank;

public record AddDepartmentRequest(
        @NotBlank(message = "Department name cannot be blank") String name,
        @NotBlank(message = "Department code cannot be blank") String code,
        String description) {

}
