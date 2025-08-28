package com.hover.project.position.mapper;

import org.mapstruct.Mapper;

import com.hover.project.position.entity.Position;
import com.hover.project.position.request.AddPositionRequest;
import com.hover.project.position.response.PositionResponse;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    Position mapToEntityFromAddPositionRequest(AddPositionRequest addPositionRequest);

    PositionResponse mapToPositionResponse(Position position);

}
