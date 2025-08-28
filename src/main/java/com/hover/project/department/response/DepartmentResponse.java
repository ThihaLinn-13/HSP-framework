package com.hover.project.department.response;

import java.util.UUID;

public record DepartmentResponse(
                UUID id,
                String name,
                String code,
                String description) {

}
