package com.hover.project.position.controller;

import com.hover.project.department.dto.DepartmentDto;
import com.hover.project.department.dto.UpdateDepartmentRequest;
import com.hover.project.position.dto.AddPositionRequest;
import com.hover.project.position.dto.PositionDto;
import com.hover.project.position.dto.UpdatePositionRequest;
import com.hover.project.position.service.PositionService;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping
    public ResponseEntity<ApiResponse<UUID>> addPosition(@Valid @RequestBody AddPositionRequest addPositionRequest) {
        var response = positionService.addPosition(addPositionRequest);
        return response.buildResponse(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResult<List<PositionDto>>>> getPositions(@PathParam("page") int page,
            @PathParam("size") int size) {
        var response = positionService.getPositions(page, size);
        return response.buildResponse(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PositionDto>> getPositionById(@PathVariable UUID id) {
        var response = positionService.getPositionById(id);
        return response.buildResponse(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UUID>> updatePosition(@PathVariable UUID id,
            @RequestBody UpdatePositionRequest position) {
        var response = positionService.updatePosition(id, position);
        return response.buildResponse(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UUID>> deletePosition(@PathVariable UUID id) {
        var response = positionService.deletePosition(id);
        return response.buildResponse(response);
    }

}
