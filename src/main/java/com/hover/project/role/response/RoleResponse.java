package com.hover.project.role.response;

import com.hover.project.role.entity.Role;

import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name,
        String code,
        String description) {

}
