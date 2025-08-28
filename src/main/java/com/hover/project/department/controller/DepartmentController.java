package com.hover.project.department.controller;

import com.hover.project.department.request.AddDepartmentRequest;
import com.hover.project.department.request.UpdateDepartmentRequest;
import com.hover.project.department.response.DepartmentResponse;
import com.hover.project.department.service.DepartmentService;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    @Caching(evict = {
            @CacheEvict(value = "departments", allEntries = true)
    })
    public ResponseEntity<ApiResponse<UUID>> addDepartment(@Valid @RequestBody AddDepartmentRequest department) {
        var response = departmentService.addDepartment(department);
        return response.buildResponse(response);
    }

    @GetMapping
    @Cacheable(value = "departments", key = "#page+ '-' + #size")
    public ResponseEntity<ApiResponse<PageResult<List<DepartmentResponse>>>> getDepartments(@PathParam("page") int page,
            @PathParam("size") int size) {
        var response = departmentService.getDepartments(page, size);
        return response.buildResponse(response);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "department", key = "#id")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getDepartmentById(@PathVariable UUID id) {
        var response = departmentService.getDepartmentById(id);
        return response.buildResponse(response);
    }

    @PatchMapping("/{id}")
    @Caching(evict = {
            @CacheEvict(value = "departments", allEntries = true),
            @CacheEvict(value = "department", key = "#id")
    })
    public ResponseEntity<ApiResponse<UUID>> updateDepartment(@PathVariable UUID id,
            @Valid @RequestBody UpdateDepartmentRequest department) {
        var response = departmentService.updateDepartment(id, department);
        return response.buildResponse(response);
    }

    @DeleteMapping("/{id}")
    @Caching(evict = {
            @CacheEvict(value = "departments", allEntries = true),
            @CacheEvict(value = "department", key = "#id")
    })
    public ResponseEntity<ApiResponse<UUID>> deleteDepartment(@PathVariable UUID id) {
        var response = departmentService.deleteDepartment(id);
        return response.buildResponse(response);
    }
}
