package com.hover.project.role.impl;

import com.hover.project.role.dao.RoleDao;
import com.hover.project.role.dto.AddRoleRequest;
import com.hover.project.role.dto.RoleDto;
import com.hover.project.role.dto.UpdateRoleRequest;
import com.hover.project.role.entity.Role;
import com.hover.project.role.service.RoleService;
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
public class RoleImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public ApiResponse<UUID> addRole(AddRoleRequest addRoleRequest) {

        Role role = addRoleRequest.mapToEntity();

        Role newRole = roleDao.save(role);

        return new ApiResponse<>(HttpStatusCode.OK, "Successfully Created Role", newRole.getId());
    }

    @Override
    public ApiResponse<PageResult<List<RoleDto>>> getRoles(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        var roles = roleDao.findAllByRecordStatus(1, pageable);

        Map<String, List<RoleDto>> roleList = new HashMap<>();
        roleList.put("roles", roles.getContent().stream().map(RoleDto::mapToDto).toList());

        PageResult<List<RoleDto>> pageResult = new PageResult<List<RoleDto>>(roles.getTotalElements(),
                roles.getTotalPages(), roles.getNumber(), roleList);

        return new ApiResponse<>(HttpStatusCode.OK, pageResult);
    }

    @Override
    public ApiResponse<RoleDto> getRoleById(UUID id) {

        var role = roleDao.findByIdAndRecordStatus(id, 1);
        if (role.isEmpty()) {
            return new ApiResponse<>(HttpStatusCode.NOT_FOUND, "Role not found.", null);
        }

        RoleDto roleDto = RoleDto.mapToDto(role.get());

        return new ApiResponse<>(HttpStatusCode.OK, roleDto);

    }

    @Override
    public ApiResponse<UUID> updateRole(UUID id, UpdateRoleRequest updateRoleRequest) {
        var role = roleDao.findByIdAndRecordStatus(id, 1);

        if (role.isEmpty()) {
            return new ApiResponse<>(HttpStatusCode.NOT_FOUND, "Role not found.", null);
        }

        Role existingRole = role.get();

        if (updateRoleRequest.code() != null)
            existingRole.setCode(updateRoleRequest.code());

        if (updateRoleRequest.name() != null)
            existingRole.setName(updateRoleRequest.name());

        if (updateRoleRequest.description() != null)
            existingRole.setDescription(updateRoleRequest.description());

        Role updatedRole = roleDao.save(existingRole);

        return new ApiResponse<>(HttpStatusCode.OK, "Successfully updated role.", updatedRole.getId());
    }

    @Override
    public ApiResponse<UUID> deleteRole(UUID id) {
        var role = roleDao.findByIdAndRecordStatus(id, 1);

        if (role.isEmpty()) {
            return new ApiResponse<>(HttpStatusCode.NOT_FOUND, "Role not found.", null);
        }

        role.get().setRecordStatus(4);
        var deletedRole = roleDao.save(role.get());

        return new ApiResponse<>(HttpStatusCode.OK, "Successfully deleted role.", deletedRole.getId());
    }
}
