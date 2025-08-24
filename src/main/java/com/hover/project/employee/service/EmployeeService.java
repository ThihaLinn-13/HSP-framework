package com.hover.project.employee.service;

import com.hover.project.employee.dto.EmployeeDto;
import com.hover.project.employee.entity.Employee;
import com.hover.project.employee.request.CreateEmployeeRequest;
import com.hover.project.util.type.ApiResponse;

import java.util.UUID;

public interface EmployeeService {

    public ApiResponse<UUID> createEmployee(CreateEmployeeRequest createEmployeeRequest);

    public ApiResponse<Employee> getEmployeeById(UUID Id);
}
