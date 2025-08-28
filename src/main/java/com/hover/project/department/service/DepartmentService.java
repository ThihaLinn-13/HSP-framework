package com.hover.project.department.service;

import com.hover.project.department.request.AddDepartmentRequest;
import com.hover.project.department.request.UpdateDepartmentRequest;
import com.hover.project.department.response.DepartmentResponse;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {

    public ApiResponse<UUID> addDepartment(AddDepartmentRequest department);

    public ApiResponse<PageResult<List<DepartmentResponse>>> getDepartments(int page, int pageSize);

    public ApiResponse<DepartmentResponse> getDepartmentById(UUID id);

    public ApiResponse<UUID> updateDepartment(UUID id, UpdateDepartmentRequest updateDepartmentRequest);

    public ApiResponse<UUID> deleteDepartment(UUID id);

}
