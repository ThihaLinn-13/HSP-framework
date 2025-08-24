package com.hover.project.role.dto;

import com.hover.project.role.entity.Role;
import jakarta.validation.constraints.NotBlank;

public record AddRoleRequest(
        @NotBlank
        String name,
        @NotBlank
        String code,
        String description
) {

        public Role mapToEntity(){
                return  new Role(this.name,this.code,this.description);
        }

}
