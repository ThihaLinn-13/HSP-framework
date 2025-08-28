package com.hover.project.employee.request;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.hover.project.util.type.Gender;
import jakarta.validation.constraints.NotNull;

public record UpdateEmployeeRequest(
                @NotNull(message = "Id is required") UUID id,
                String employeeId,
                String userName,
                String email,
                Gender gender,
                LocalDate dateOfBirth,
                String nrcNumber,
                LocalDate dateOfEmployment,
                UUID positionId,
                UUID departmentId,
                List<UUID> roleIds) {

}
