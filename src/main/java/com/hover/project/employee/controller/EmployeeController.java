package com.hover.project.employee.controller;

import com.hover.project.employee.dto.EmployeeDto;
import com.hover.project.employee.request.CreateEmployeeRequest;
import com.hover.project.employee.service.EmployeeService;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;
import jakarta.websocket.server.PathParam;

import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    public ResponseEntity<ApiResponse<UUID>> AddEmployee(@RequestBody CreateEmployeeRequest createEmployeeRequest) {
        var response = employeeService.createEmployee(createEmployeeRequest);
        return response.buildResponse(response);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "employee", key = "#id")
    public ResponseEntity<ApiResponse<EmployeeDto>> getEmployee(@PathVariable UUID id) {
        var response = employeeService.getEmployeeById(id);
        return response.buildResponse(response);
    }

    @GetMapping
    @Cacheable(value = "employees", key = "#page + '-' + #size")
    public ResponseEntity<ApiResponse<PageResult<List<EmployeeDto>>>> getEmployees(@RequestParam("page") int page,
            @RequestParam("size") int size) {
        var response = employeeService.getEmployees(page, size);
        return response.buildResponse(response);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "employees", allEntries = true)
    public ResponseEntity<ApiResponse<UUID>> deleteEmployee(@RequestParam UUID id) {
        var response = employeeService.deleteEmployee(id);
        return response.buildResponse(response);
    }

}
