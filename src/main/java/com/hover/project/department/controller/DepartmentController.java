package com.hover.project.department.controller;

import com.hover.project.department.dto.AddDepartmentRequest;
import com.hover.project.department.dto.DepartmentDto;
import com.hover.project.department.dto.UpdateDepartmentRequest;
import com.hover.project.department.service.DepartmentService;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ApiResponse<UUID>> addDepartment(@RequestBody AddDepartmentRequest department) {
        var response = departmentService.addDepartment(department);
        return response.buildResponse(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResult<List<DepartmentDto>>>> getDepartments(@PathParam("page") int page,
            @PathParam("size") int size) {
        var response = departmentService.getDepartments(page, size);
        return response.buildResponse(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDto>> getDepartmentById(@PathVariable UUID id) {
        var response = departmentService.getDepartmentById(id);
        return response.buildResponse(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UUID>> updateDepartment(@PathVariable UUID id,
            @RequestBody UpdateDepartmentRequest department) {
        var response = departmentService.updateDepartment(id, department);
        return response.buildResponse(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UUID>> deleteDepartment(@PathVariable UUID id) {
        var response = departmentService.deleteDepartment(id);
        return response.buildResponse(response);
    }
}
