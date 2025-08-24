package com.hover.project.department.dto;

import com.hover.project.department.entity.Department;

import java.util.UUID;

public record DepartmentDto(
        UUID id,
        String name,
        String code,
        String description
) {

    public static DepartmentDto mapToDto(Department department){
        return new DepartmentDto(department.getId(),department.getName(),department.getCode(),department.getDescription());
    }

    public Department mapToEntity(Department department){
        return new Department();
    }
}
