package com.hover.project.employee.request;

import com.hover.project.department.entity.Department;
import com.hover.project.employee.entity.Employee;
import com.hover.project.position.entity.Position;
import com.hover.project.role.entity.Role;
import com.hover.project.util.type.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.beans.Encoder;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public record CreateEmployeeRequest(
        @NotBlank(message = "Employee ID is required")
        String employeeId,

        @NotBlank(message = "User name is required")
        String userName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @NotNull(message = "Gender is required")
        Gender gender,

        LocalDate dateOfBirth,

        String nrcNumber,

        LocalDate dateOfEmployment,

        UUID positionId,

        UUID departmentId,

        List<UUID> roleIds
) {


    public Employee mapToEntity(PasswordEncoder passwordEncoder, Position position, Department department, List<Role> roles) {
        Employee employee = new Employee();
        employee.setEmployeeId(this.employeeId);
        employee.setUserName(this.userName);
        employee.setEmail(this.email);
        employee.setPassword(passwordEncoder.encode(this.password)); // encode password
        employee.setGender(this.gender);
        employee.setDateOfBirth(this.dateOfBirth);
        employee.setNrcNumber(this.nrcNumber);
        employee.setDateOfEmployment(this.dateOfEmployment);
        employee.addPosition(position);
        employee.addDepartment(department);
        employee.setRoles(roles);
        return employee;
    }

}
