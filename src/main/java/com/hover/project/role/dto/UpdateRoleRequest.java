package com.hover.project.role.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateRoleRequest(

        @NotBlank String name,
        @NotBlank String code,
        String description

) {

}
