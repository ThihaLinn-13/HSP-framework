package com.hover.project.role.dto;

import java.util.UUID;

import com.hover.project.role.entity.Role;

public record RoleNameDto(
        UUID id,
        String name) {

    public static RoleNameDto mapToEntity(Role role) {
        return new RoleNameDto(role.getId(), role.getName());
    }

}
