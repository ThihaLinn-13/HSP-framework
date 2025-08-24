package com.hover.project.position.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePositionRequest(
        @NotBlank(message = "Department name cannot be blank") String name,
        @NotBlank(message = "Department code cannot be blank") String code,
        String description) {

}
