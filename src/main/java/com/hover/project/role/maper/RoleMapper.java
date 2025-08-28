package com.hover.project.role.maper;

import org.mapstruct.Mapper;

import com.hover.project.role.entity.Role;
import com.hover.project.role.request.AddRoleRequest;
import com.hover.project.role.response.RoleResponse;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role mapToEntityFromAddRoleRequest(AddRoleRequest addRoleRequest);

    RoleResponse mapToRoleResponse(Role role);

}
