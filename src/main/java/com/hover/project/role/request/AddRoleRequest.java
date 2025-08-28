package com.hover.project.role.request;

import com.hover.project.role.entity.Role;
import jakarta.validation.constraints.NotBlank;

public record AddRoleRequest(
                @NotBlank(message = "Role name is required") String name,
                @NotBlank(message = "Role code is required") String code,
                String description) {

        public Role mapToEntity() {
                return new Role(this.name, this.code, this.description);
        }

}
