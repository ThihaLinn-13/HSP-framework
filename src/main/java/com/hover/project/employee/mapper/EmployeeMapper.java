package com.hover.project.employee.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hover.project.department.dao.DepartmentDao;
import com.hover.project.department.entity.Department;
import com.hover.project.employee.entity.Employee;
import com.hover.project.employee.request.CreateEmployeeRequest;
import com.hover.project.employee.request.UpdateEmployeeRequest;
import com.hover.project.employee.response.EmployeeResponse;
import com.hover.project.position.dao.PositionDao;
import com.hover.project.position.entity.Position;
import com.hover.project.role.dao.RoleDao;
import com.hover.project.role.entity.Role;

@Mapper(componentModel = "spring")
public abstract class EmployeeMapper {

    @Autowired
    protected PositionDao positionDao;

    @Autowired
    protected DepartmentDao departmentDao;

    @Autowired
    protected RoleDao roleDao;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mappings({
            @Mapping(target = "position", source = "position"),
            @Mapping(target = "department", source = "department"),
            @Mapping(target = "roles", source = "roles")
    })
    public abstract EmployeeResponse mapToEmployeeResponse(Employee employee);

    @Mappings({
            @Mapping(target = "position", expression = "java(getPositionOrNullOrThrow(createEmployeeRequest.positionId()))"),
            @Mapping(target = "department", expression = "java(getDepartmentOrNullOrThrow(createEmployeeRequest.departmentId()))"),
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(createEmployeeRequest.password()))"),
            @Mapping(target = "roles", expression = "java(getRolesOrEmptyList(createEmployeeRequest.roleIds()))")
    })
    public abstract Employee mapToEntityFromCreateEmployeeRequest(CreateEmployeeRequest createEmployeeRequest);

    @Mappings({
            @Mapping(target = "position", expression = "java(getPositionOrNullOrThrow(updateEmployeeRequest.positionId()))"),
            @Mapping(target = "department", expression = "java(getDepartmentOrNullOrThrow(updateEmployeeRequest.departmentId()))"),
            @Mapping(target = "roles", expression = "java(getRolesOrEmptyList(updateEmployeeRequest.roleIds()))")
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Employee mapToEntityFromUpdateEmployeeRequest(UpdateEmployeeRequest updateEmployeeRequest);

    protected Position getPositionOrNullOrThrow(UUID positionId) {
        if (positionId == null) {
            return null;
        }
        return positionDao.findByIdAndRecordStatus(positionId, 1)
                .orElseThrow(() -> new IllegalArgumentException("Invalid positionId: " + positionId));
    }

    protected Department getDepartmentOrNullOrThrow(UUID departmentId) {
        if (departmentId == null) {
            return null;
        }
        return departmentDao.findByIdAndRecordStatus(departmentId, 1)
                .orElseThrow(() -> new IllegalArgumentException("Invalid departmentId: " + departmentId));
    }

    protected List<Role> getRolesOrEmptyList(List<UUID> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return new ArrayList<>();
        }
        return roleDao.findAllByIdInAndRecordStatus(roleIds, 1);
    }

}
