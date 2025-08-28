package com.hover.project.position.request;

import jakarta.validation.constraints.NotBlank;

public record AddPositionRequest(

                @NotBlank(message = "Position name cannot be blank") String name,

                @NotBlank(message = "Position code cannot be blank") String code,

                String description) {

}
