package com.hover.project.menu.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddMenuRequest(
        @NotNull(message = "Menu name can't be null")
        String name,
        @NotNull(message = "Menu path can't be null" )
        String path,
        UUID menuId
) {
}
