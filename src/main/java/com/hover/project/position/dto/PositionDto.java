package com.hover.project.position.dto;

import com.hover.project.position.entity.Position;

import java.util.UUID;

public record PositionDto(
        UUID id,
        String name,
        String code,
        String description) {

    public static PositionDto mapToDto(Position position) {
        return new PositionDto(
                position.getId(),
                position.getName(),
                position.getCode(),
                position.getDescription());
    }

}
