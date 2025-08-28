package com.hover.project.employee.controller;

import com.hover.project.employee.request.CreateEmployeeRequest;
import com.hover.project.employee.request.UpdateEmployeeRequest;
import com.hover.project.employee.response.EmployeeResponse;
import com.hover.project.employee.service.EmployeeService;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping()
    @Caching(evict = {
            @CacheEvict(value = "employees", allEntries = true),
    })
    public ResponseEntity<ApiResponse<UUID>> createEmployee(
            @Valid @RequestBody CreateEmployeeRequest createEmployeeRequest) {
        var response = employeeService.createEmployee(createEmployeeRequest);
        return response.buildResponse(response);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "employee", key = "#id")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployee(@PathVariable UUID id) {
        var response = employeeService.getEmployeeById(id);
        return response.buildResponse(response);
    }

    @GetMapping
    @Cacheable(value = "employees", key = "#page + '-' + #size")
    public ResponseEntity<ApiResponse<PageResult<List<EmployeeResponse>>>> getEmployees(@RequestParam("page") int page,
            @RequestParam("size") int size) {
        var response = employeeService.getEmployees(page, size);
        return response.buildResponse(response);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "employees", allEntries = true)
    public ResponseEntity<ApiResponse<UUID>> deleteEmployee(@PathVariable UUID id) {
        var response = employeeService.deleteEmployee(id);
        return response.buildResponse(response);
    }

    @PatchMapping
    @Caching(evict = {
            @CacheEvict(value = "employees", allEntries = true),
            @CacheEvict(value = "employee", key = "#updateEmployeeRequest.id")
    })
    public ResponseEntity<ApiResponse<UUID>> updateEmployee(
            @Valid @RequestBody UpdateEmployeeRequest updateEmployeeRequest) {
        var response = employeeService.updateEmployee(updateEmployeeRequest);
        return response.buildResponse(response);
    }

}
