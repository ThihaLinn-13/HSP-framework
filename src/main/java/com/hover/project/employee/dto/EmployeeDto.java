package com.hover.project.employee.dto;

import com.hover.project.department.dto.DepartmentDto;
import com.hover.project.employee.entity.Employee;
import com.hover.project.position.dto.PositionDto;
import com.hover.project.role.dto.RoleDto;
import com.hover.project.util.type.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record EmployeeDto(
                UUID id,
                String employeeId,
                String userName,
                String email,
                Gender gender,
                LocalDate dateOfBirth,
                String nrcNumber,
                LocalDate dateOfEmployment,
                PositionDto positon,
                DepartmentDto department,
                List<RoleDto> roles) {

}
