package com.hover.project.role.controller;

import com.hover.project.position.dto.PositionDto;
import com.hover.project.position.dto.UpdatePositionRequest;
import com.hover.project.role.dto.AddRoleRequest;
import com.hover.project.role.dto.RoleDto;
import com.hover.project.role.dto.UpdateRoleRequest;
import com.hover.project.role.service.RoleService;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;

import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<UUID>> addRole(@RequestBody AddRoleRequest addRoleRequest) {
        var response = roleService.addRole(addRoleRequest);
        return response.buildResponse(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResult<List<RoleDto>>>> geRoles(@PathParam("page") int page,
            @PathParam("size") int size) {
        var response = roleService.getRoles(page, size);
        return response.buildResponse(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleDto>> getRoleById(@PathVariable UUID id) {
        var response = roleService.getRoleById(id);
        return response.buildResponse(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UUID>> updateRole(@PathVariable UUID id,
            @RequestBody UpdateRoleRequest role) {
        var response = roleService.updateRole(id, role);
        return response.buildResponse(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UUID>> deleteRole(@PathVariable UUID id) {
        var response = roleService.deleteRole(id);
        return response.buildResponse(response);
    }

}
