package com.hover.project.position.impl;

import com.hover.project.position.dao.PositionDao;
import com.hover.project.position.entity.Position;
import com.hover.project.position.mapper.PositionMapper;
import com.hover.project.position.request.AddPositionRequest;
import com.hover.project.position.request.UpdatePositionRequest;
import com.hover.project.position.response.PositionResponse;
import com.hover.project.position.service.PositionService;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.HttpStatusCode;
import com.hover.project.util.type.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PositionImpl implements PositionService {

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public ApiResponse<UUID> addPosition(AddPositionRequest addPositionRequest) {
        Position position = positionMapper.mapToEntityFromAddPositionRequest(addPositionRequest);
        Position newPosition = positionDao.save(position);
        return new ApiResponse<>(HttpStatusCode.OK, "Successfully created position", newPosition.getId());
    }

    @Override
    public ApiResponse<PageResult<List<PositionResponse>>> getPositions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        var roles = positionDao.findAllByRecordStatus(1, pageable);

        System.out.println(roles.getTotalPages());

        Map<String, List<PositionResponse>> positionList = new HashMap<>();
        positionList.put("positions", roles.getContent().stream().map(positionMapper::mapToPositionResponse).toList());

        PageResult<List<PositionResponse>> pageResult = new PageResult<List<PositionResponse>>(roles.getTotalElements(),
                roles.getTotalPages(), roles.getNumber(), positionList);

        return new ApiResponse<>(HttpStatusCode.OK, pageResult);
    }

    @Override
    public ApiResponse<PositionResponse> getPositionById(UUID id) {

        var position = positionDao.findByIdAndRecordStatus(id, 1);

        if (position.isEmpty()) {
            return new ApiResponse<>(HttpStatusCode.NOT_FOUND, "Position not found.", null);
        }

        PositionResponse positionDto = positionMapper.mapToPositionResponse(position.get());

        return new ApiResponse<>(HttpStatusCode.OK, positionDto);
    }

    @Override
    public ApiResponse<UUID> updatePosition(UUID id, UpdatePositionRequest updateDepartmentRequest) {
        var position = positionDao.findByIdAndRecordStatus(id, 1);

        if (position.isEmpty()) {
            return new ApiResponse<>(HttpStatusCode.NOT_FOUND, "Position not found.", null);
        }

        Position existingPosition = position.get();

        if (updateDepartmentRequest.code() != null)
            existingPosition.setCode(updateDepartmentRequest.code());

        if (updateDepartmentRequest.name() != null)
            existingPosition.setName(updateDepartmentRequest.name());

        if (updateDepartmentRequest.description() != null)
            existingPosition.setDescription(updateDepartmentRequest.description());

        Position updatedPosition = positionDao.save(existingPosition);

        return new ApiResponse<>(HttpStatusCode.OK, "Successfully updated position.", updatedPosition.getId());
    }

    @Override
    public ApiResponse<UUID> deletePosition(UUID id) {
        var position = positionDao.findByIdAndRecordStatus(id, 1);

        if (position.isEmpty()) {
            return new ApiResponse<>(HttpStatusCode.NOT_FOUND, "Position not found.", null);
        }

        position.get().setRecordStatus(4);
        var deletedPosition = positionDao.save(position.get());

        return new ApiResponse<>(HttpStatusCode.OK, "Successfully deleted position.", deletedPosition.getId());
    }
}
