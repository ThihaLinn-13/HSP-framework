package com.hover.project.position.dto;

import com.hover.project.position.entity.Position;
import jakarta.validation.constraints.NotBlank;

public record AddPositionRequest(

        @NotBlank(message = "Position name cannot be blank") String name,

        @NotBlank(message = "Position code cannot be blank") String code,

        String description) {

    public Position mapToEntity() {
        return new Position(this.name, this.code, this.description);
    }

}
