package com.hover.project.position.service;

import com.hover.project.position.request.AddPositionRequest;
import com.hover.project.position.request.UpdatePositionRequest;
import com.hover.project.position.response.PositionResponse;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.PageResult;

import java.util.List;
import java.util.UUID;

public interface PositionService {

    public ApiResponse<UUID> addPosition(AddPositionRequest addPositionRequest);

    public ApiResponse<PageResult<List<PositionResponse>>> getPositions(int page, int size);

    public ApiResponse<PositionResponse> getPositionById(UUID id);

    public ApiResponse<UUID> updatePosition(UUID id, UpdatePositionRequest updatePositionRequest);

    public ApiResponse<UUID> deletePosition(UUID id);

}
