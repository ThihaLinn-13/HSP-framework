package com.hover.project.role.service;

import com.hover.project.role.dto.AddRoleRequest;
import com.hover.project.role.dto.RoleDto;
import com.hover.project.role.dto.UpdateRoleRequest;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    public ApiResponse<UUID> addRole(AddRoleRequest addRoleRequest);

    public ApiResponse<PageResult<List<RoleDto>>> getRoles(int page, int size);

    public ApiResponse<RoleDto> getRoleById(UUID id);

    public ApiResponse<UUID> updateRole(UUID id, UpdateRoleRequest updateRoleRequest);

    public ApiResponse<UUID> deleteRole(UUID id);

}
