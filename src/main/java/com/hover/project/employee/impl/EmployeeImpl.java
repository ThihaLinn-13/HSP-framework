package com.hover.project.employee.impl;

import com.hover.project.department.dao.DepartmentDao;
import com.hover.project.department.dto.DepartmentDto;
import com.hover.project.department.entity.Department;
import com.hover.project.employee.dao.EmployeeDao;
import com.hover.project.employee.entity.Employee;
import com.hover.project.employee.request.CreateEmployeeRequest;
import com.hover.project.employee.service.EmployeeService;
import com.hover.project.position.dao.PositionDao;
import com.hover.project.position.dto.PositionDto;
import com.hover.project.position.entity.Position;
import com.hover.project.role.dao.RoleDao;
import com.hover.project.role.dto.RoleDto;
import com.hover.project.role.entity.Role;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.HttpStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeImpl implements EmployeeService {

        @Autowired
        private EmployeeDao employeeDao;

        @Autowired
        private PositionDao positionDao;

        @Autowired
        private DepartmentDao departmentDao;

        @Autowired
        private RoleDao roleDao;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        public ApiResponse<UUID> createEmployee(CreateEmployeeRequest createEmployeeRequest) {

                Position position = positionDao.findById(createEmployeeRequest.positionId())
                                .orElseThrow(() -> new IllegalArgumentException("Invalid position"));

                Department department = departmentDao.findById(createEmployeeRequest.departmentId())
                                .orElseThrow(() -> new IllegalArgumentException("Invalid department"));

                List<Role> roles = (createEmployeeRequest.roleIds() == null
                                || createEmployeeRequest.roleIds().isEmpty())
                                                ? new ArrayList<>()
                                                : roleDao.findAllById(createEmployeeRequest.roleIds());

                Employee employee = createEmployeeRequest.mapToEntity(passwordEncoder, position, department, roles);

                Employee newEmployee = employeeDao.save(employee);

                return new ApiResponse<UUID>(HttpStatusCode.CREATED, "Created Employee Successfully.",
                                newEmployee.getId());
        }

        @Override
        public ApiResponse<Employee> getEmployeeById(UUID id) {

                Employee employee = employeeDao.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee Id"));

                PositionDto positon = new PositionDto(
                                employee.getPosition().getId(),
                                employee.getPosition().getName(),
                                employee.getPosition().getCode(),
                                employee.getPosition().getDescription());

                DepartmentDto department = new DepartmentDto(
                                employee.getDepartment().getId(),
                                employee.getDepartment().getName(),
                                employee.getDepartment().getCode(),
                                employee.getDepartment().getDescription());

                List<Role> roles = roleDao.findByEmployeesId(id);

                List<RoleDto> roleDtos = roles.stream().map(role -> {
                        var roleDto = new RoleDto(role.getId(), role.getName(), role.getCode(), role.getDescription());
                        return roleDto;
                }).toList();

                employee.setRoles(roles);

                return new ApiResponse<>(HttpStatusCode.OK, employee);
        }
}
