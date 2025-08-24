package com.hover.project.department.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateDepartmentRequest(
        @NotBlank(message = "Department name cannot be blank") String name,
        @NotBlank(message = "Department code cannot be blank") String code,
        String description) {

}
