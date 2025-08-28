package com.hover.project.department.request;

public record UpdateDepartmentRequest(
        String name,
        String code,
        String description) {

}
