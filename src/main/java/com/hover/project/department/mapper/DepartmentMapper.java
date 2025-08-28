package com.hover.project.department.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.hover.project.department.dto.DepartmentNameDto;
import com.hover.project.department.entity.Department;
import com.hover.project.department.request.AddDepartmentRequest;
import com.hover.project.department.response.DepartmentResponse;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentResponse mapToDto(Department department);

    DepartmentNameDto mapToNameDto(Department department);

    Department mapToEntityFromAddDepartmentRequest(AddDepartmentRequest department);

}
