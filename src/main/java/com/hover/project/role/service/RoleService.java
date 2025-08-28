package com.hover.project.role.service;

import com.hover.project.role.request.AddRoleRequest;
import com.hover.project.role.request.UpdateRoleRequest;
import com.hover.project.role.response.RoleResponse;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    public ApiResponse<UUID> addRole(AddRoleRequest addRoleRequest);

    public ApiResponse<PageResult<List<RoleResponse>>> getRoles(int page, int size);

    public ApiResponse<RoleResponse> getRoleById(UUID id);

    public ApiResponse<UUID> updateRole(UUID id, UpdateRoleRequest updateRoleRequest);

    public ApiResponse<UUID> deleteRole(UUID id);

}
