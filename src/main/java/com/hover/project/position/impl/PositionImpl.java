package com.hover.project.position.impl;

import com.hover.project.department.dto.DepartmentDto;
import com.hover.project.department.entity.Department;
import com.hover.project.position.dao.PositionDao;
import com.hover.project.position.dto.AddPositionRequest;
import com.hover.project.position.dto.PositionDto;
import com.hover.project.position.dto.UpdatePositionRequest;
import com.hover.project.position.entity.Position;
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

    @Override
    public ApiResponse<UUID> addPosition(AddPositionRequest addPositionRequest) {
        Position position = addPositionRequest.mapToEntity();
        Position newPosition = positionDao.save(position);
        return new ApiResponse<>(HttpStatusCode.OK, "Successfully created position", newPosition.getId());
    }

    @Override
    public ApiResponse<PageResult<List<PositionDto>>> getPositions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        var roles = positionDao.findAllByRecordStatus(1, pageable);

        System.out.println(roles.getTotalPages());

        Map<String, List<PositionDto>> positionList = new HashMap<>();
        positionList.put("positions", roles.getContent().stream().map(PositionDto::mapToDto).toList());

        PageResult<List<PositionDto>> pageResult = new PageResult<List<PositionDto>>(roles.getTotalElements(),
                roles.getTotalPages(), roles.getNumber(), positionList);

        return new ApiResponse<>(HttpStatusCode.OK, pageResult);
    }

    @Override
    public ApiResponse<PositionDto> getPositionById(UUID id) {

        var position = positionDao.findByIdAndRecordStatus(id, 1);

        if (position.isEmpty()) {
            return new ApiResponse<>(HttpStatusCode.NOT_FOUND, "Position not found.", null);
        }

        PositionDto positionDto = PositionDto.mapToDto(position.get());

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
