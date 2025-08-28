package com.hover.project.employee.response;

import com.hover.project.department.dto.DepartmentNameDto;
import com.hover.project.employee.entity.Employee;
import com.hover.project.position.dto.PositionNameDto;
import com.hover.project.role.dto.RoleNameDto;
import com.hover.project.util.type.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record EmployeeResponse(
                UUID id,
                String employeeId,
                String userName,
                String email,
                Gender gender,
                LocalDate dateOfBirth,
                String nrcNumber,
                LocalDate dateOfEmployment,
                PositionNameDto position,
                DepartmentNameDto department,
                List<RoleNameDto> roles) {

        public static EmployeeResponse mapToEntity(Employee employee, List<RoleNameDto> roles) {
                return new EmployeeResponse(
                                employee.getId(),
                                employee.getEmployeeId(),
                                employee.getUserName(),
                                employee.getEmail(),
                                employee.getGender(),
                                employee.getDateOfBirth(),
                                employee.getNrcNumber(),
                                employee.getDateOfEmployment(),
                                new PositionNameDto(
                                                employee.getPosition().getId(),
                                                employee.getPosition().getName()),

                                new DepartmentNameDto(
                                                employee.getDepartment().getId(),
                                                employee.getDepartment().getName()),
                                roles);
        }

}
