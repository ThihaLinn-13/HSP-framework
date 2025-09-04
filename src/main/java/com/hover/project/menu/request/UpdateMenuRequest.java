package com.hover.project.menu.request;

import java.util.UUID;

public record UpdateMenuRequest(
        String name,
        String path,
        UUID menuId
) {
}
