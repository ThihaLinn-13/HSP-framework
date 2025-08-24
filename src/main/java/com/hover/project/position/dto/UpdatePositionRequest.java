package com.hover.project.position.dto;

import jakarta.validation.constraints.NotNull;

public record UpdatePositionRequest(
                @NotNull(message = "Department name cannot be null") String name,
                @NotNull(message = "Department code cannot be null") String code,
                String description) {

}
