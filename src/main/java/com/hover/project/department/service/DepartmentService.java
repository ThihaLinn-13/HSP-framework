package com.hover.project.department.service;

import com.hover.project.department.dto.AddDepartmentRequest;
import com.hover.project.department.dto.DepartmentDto;
import com.hover.project.department.dto.UpdateDepartmentRequest;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {

    public ApiResponse<UUID> addDepartment(AddDepartmentRequest department);

    public ApiResponse<PageResult<List<DepartmentDto>>> getDepartments(int page, int pageSize);

    public ApiResponse<DepartmentDto> getDepartmentById(UUID id);

    public ApiResponse<UUID> updateDepartment(UUID id, UpdateDepartmentRequest updateDepartmentRequest);

    public ApiResponse<UUID> deleteDepartment(UUID id);

}
