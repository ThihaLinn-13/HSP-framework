package com.hover.project.role.request;

import jakarta.validation.constraints.NotNull;

public record UpdateRoleRequest(

                @NotNull String name,
                @NotNull String code,
                String description

) {

}
