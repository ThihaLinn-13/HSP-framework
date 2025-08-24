package com.hover.project.role.dto;

import com.hover.project.role.entity.Role;

import java.util.UUID;

public record RoleDto(
        UUID id,
        String name,
        String code,
        String description
) {

    public static RoleDto mapToDto(Role role){
        return new RoleDto(role.getId(),role.getName(),role.getCode(),role.getDescription());
    }

}
