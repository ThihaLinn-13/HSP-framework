package com.hover.project.employee.service;

import com.hover.project.employee.request.CreateEmployeeRequest;
import com.hover.project.employee.request.UpdateEmployeeRequest;
import com.hover.project.employee.response.EmployeeResponse;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    public ApiResponse<UUID> createEmployee(CreateEmployeeRequest createEmployeeRequest);

    public ApiResponse<EmployeeResponse> getEmployeeById(UUID Id);

    public ApiResponse<PageResult<List<EmployeeResponse>>> getEmployees(int page, int size);

    public ApiResponse<UUID> deleteEmployee(UUID id);

    public ApiResponse<UUID> updateEmployee(UpdateEmployeeRequest updateEmployeeRequest);

}
