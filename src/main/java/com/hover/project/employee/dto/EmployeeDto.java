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

    public static EmployeeDto mapToEntity(Employee employee, List<RoleDto> roles) {
        return new EmployeeDto(
                employee.getId(),
                employee.getEmployeeId(),
                employee.getUserName(),
                employee.getEmail(),
                employee.getGender(),
                employee.getDateOfBirth(),
                employee.getNrcNumber(),
                employee.getDateOfEmployment(),
                new PositionDto(
                        employee.getPosition().getId(),
                        employee.getPosition().getName(),
                        employee.getPosition().getCode(),
                        employee.getPosition().getDescription()),
                new DepartmentDto(
                        employee.getDepartment().getId(),
                        employee.getDepartment().getName(),
                        employee.getDepartment().getCode(),
                        employee.getDepartment().getDescription()),
                roles);
    }

}
