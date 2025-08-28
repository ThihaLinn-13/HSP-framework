package com.hover.project.position.response;

import com.hover.project.position.entity.Position;

import java.util.UUID;

public record PositionResponse(
                UUID id,
                String name,
                String code,
                String description) {

}
