package com.hover.project.menu.impl;

import com.hover.project.menu.dao.MenuDao;
import com.hover.project.menu.entity.Menu;
import com.hover.project.menu.mapper.MenuMapper;
import com.hover.project.menu.request.AddMenuRequest;
import com.hover.project.menu.request.UpdateMenuRequest;
import com.hover.project.menu.response.MenuResponse;
import com.hover.project.menu.service.MenuService;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.HttpStatusCode;
import com.hover.project.util.type.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class MenuImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public ApiResponse<UUID> addMenu(AddMenuRequest addMenuRequest) {
        Menu menu = null;
        if (addMenuRequest.menuId() != null) {
           menu = menuDao.findByIdAndRecordStatus(addMenuRequest.menuId(),1).orElseThrow(
                   ()-> new IllegalArgumentException("Menu not exit")
           );
        }

        var toAddMenu = menuMapper.mapToEntityFromAddMenuRequest(addMenuRequest);
        toAddMenu.setMenu(menu);
        var savedMenu = menuDao.save(toAddMenu);
        return new ApiResponse<>(HttpStatusCode.CREATED, savedMenu.getId());
    }

    @Override
    public ApiResponse<UUID> removeMenu(UUID id) {
        var menu = menuDao.findByIdAndRecordStatus(id, 1);

        if (menu.isEmpty()) {
            return new ApiResponse<>(HttpStatusCode.NOT_FOUND, "Menu not found.", null);
        }

        menu.get().setRecordStatus(4);
        var deletedRole = menuDao.save(menu.get());

        return new ApiResponse<>(HttpStatusCode.OK, "Successfully deleted menu.", deletedRole.getId());
    }

    @Override
    public ApiResponse<PageResult<List<MenuResponse>>> getMenus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        var menus = menuDao.findAllByRecordStatus(1,pageable);

        Map<String, List<MenuResponse>> menuList = new HashMap<>();
        menuList.put("menus", menuMapper.mapToMenuResponses(menus.getContent()));

        PageResult<List<MenuResponse>> pageResult = new PageResult<List<MenuResponse>>(menus.getTotalElements(),
                menus.getTotalPages(), menus.getNumber(), menuList);

        return new ApiResponse<>(HttpStatusCode.OK, "Successfully fetched data", pageResult);
    }

    @Override
    public ApiResponse<MenuResponse> getMenu(UUID menuId) {
        var menu = menuDao.findByIdAndRecordStatus(menuId, 1);
        if (menu.isEmpty()) {
            return new ApiResponse<>(HttpStatusCode.NOT_FOUND, "Menu not found.", null);
        }
        var responseMenu = menuMapper.mapToMenuResponseFromEntity(menu.get());
        return new ApiResponse<>(HttpStatusCode.OK, "Successfully get data", responseMenu);
    }

    @Override
    public ApiResponse<UUID> updateMenu(UUID menuId, UpdateMenuRequest updateMenuRequest) {
        var menu = menuDao.findByIdAndRecordStatus(menuId, 1)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found")
                );

        menuMapper.updateMenuFromDto(updateMenuRequest,menu);

        menuDao.save(menu);

        return new ApiResponse<>(HttpStatusCode.OK,"Successfully updated menu",menu.getId());
    }
}
