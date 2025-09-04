package com.hover.project.menu.response;


import com.hover.project.menu.dto.MenuDto;

import java.util.List;
import java.util.UUID;

public record MenuResponse(
        UUID id,
        String name,
        String path,
        List<MenuResponse> subMenus
) {
}
