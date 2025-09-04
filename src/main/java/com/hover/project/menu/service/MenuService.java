package com.hover.project.menu.service;

import com.hover.project.menu.request.AddMenuRequest;
import com.hover.project.menu.request.UpdateMenuRequest;
import com.hover.project.menu.response.MenuResponse;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface MenuService {

    public ApiResponse<UUID> addMenu(AddMenuRequest addMenuRequest);

    public ApiResponse<UUID> removeMenu(UUID uuid);

    public ApiResponse<PageResult<List<MenuResponse>>> getMenus(int page,int size);

    public ApiResponse<MenuResponse> getMenu(UUID menuId);

    public ApiResponse<UUID> updateMenu(UUID menuId,UpdateMenuRequest updateMenuRequest);

}
