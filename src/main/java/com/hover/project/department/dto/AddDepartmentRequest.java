package com.hover.project.department.dto;

import com.hover.project.department.entity.Department;

import jakarta.validation.constraints.NotBlank;

public record AddDepartmentRequest(
        @NotBlank(message = "Department name cannot be blank") String name,
        @NotBlank(message = "Department code cannot be blank") String code,
        String description) {

    public Department mapToEntity() {
        return new Department(this.name, this.code, this.description);
    }

}
