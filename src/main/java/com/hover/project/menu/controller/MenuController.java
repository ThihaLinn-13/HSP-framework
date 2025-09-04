package com.hover.project.menu.controller;

import com.hover.project.menu.entity.Menu;
import com.hover.project.menu.request.AddMenuRequest;
import com.hover.project.menu.request.UpdateMenuRequest;
import com.hover.project.menu.response.MenuResponse;
import com.hover.project.menu.service.MenuService;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping
    @CacheEvict(value = "menus",allEntries = true)
    public ResponseEntity<ApiResponse<UUID>> addMenu(@RequestBody AddMenuRequest addMenuRequest){
        var response = menuService.addMenu(addMenuRequest);
        return  response.buildResponse(response);
    }

    @GetMapping
    @Cacheable(value = "menus", key = "#page+ '-' + #size")
    public  ResponseEntity<ApiResponse<PageResult<List<MenuResponse>>>> getMenus(@RequestParam("page") int page,
                                                                   @RequestParam("size") int size){
        var response = menuService.getMenus(page,size);
        return response.buildResponse(response);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "menu", key = "#id")
    public  ResponseEntity<ApiResponse<MenuResponse>> getMenu(@PathVariable UUID id){
        var response = menuService.getMenu(id);
        return response.buildResponse(response);
    }

    @DeleteMapping("/{id}")
    @Caching(evict = {
            @CacheEvict(value = "menus", allEntries = true),
            @CacheEvict(value = "menu", key = "#id")
    })
    public ResponseEntity<ApiResponse<UUID>> removeMenu(@PathVariable UUID id){
        var response = menuService.removeMenu(id);
        return response.buildResponse(response);
    }

    @PatchMapping("/{id}")
    @Caching(evict = {
            @CacheEvict(value = "menus", allEntries = true),
            @CacheEvict(value = "menu", key = "#id")
    })
    public ResponseEntity<ApiResponse<UUID>> updateMenuRequest(@PathVariable UUID id, @RequestBody UpdateMenuRequest updateMenuRequest){
        var response = menuService.updateMenu(id,updateMenuRequest);
        return response.buildResponse(response);
    }

}
