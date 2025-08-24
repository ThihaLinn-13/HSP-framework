package com.hover.project.department.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateDepartmentRequest(
        @NotNull(message = "Department name cannot be null") String name,
        @NotNull(message = "Department code cannot be null") String code,
        String description) {

}
