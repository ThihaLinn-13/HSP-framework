package com.hover.project.employee.impl;

import com.hover.project.department.dao.DepartmentDao;
import com.hover.project.department.dto.DepartmentDto;
import com.hover.project.department.entity.Department;
import com.hover.project.employee.dao.EmployeeDao;
import com.hover.project.employee.dto.EmployeeDto;
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
import com.hover.project.util.type.PageResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

                Position position = positionDao.findByIdAndRecordStatus(createEmployeeRequest.positionId(), 1)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid position"));

                Department department = departmentDao.findByIdAndRecordStatus(createEmployeeRequest.departmentId(), 1)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid department"));

                List<Role> roles = (createEmployeeRequest.roleIds() == null
                                || createEmployeeRequest.roleIds().isEmpty())
                                                ? new ArrayList<>()
                                                : roleDao.findAllByIdInAndRecordStatus(createEmployeeRequest.roleIds(),
                                                                1);

                Employee employee = createEmployeeRequest.mapToEntity(passwordEncoder, position, department, roles);

                Employee newEmployee = employeeDao.save(employee);

                return new ApiResponse<UUID>(HttpStatusCode.CREATED, "Created Employee Successfully.",
                                newEmployee.getId());
        }

        @Override
        public ApiResponse<EmployeeDto> getEmployeeById(UUID id) {

                Employee employee = employeeDao.findByIdAndRecordStatus(id, 1)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee Id"));

                List<Role> roles = roleDao.findByEmployeesIdAndRecordStatus(id, 1);

                List<RoleDto> roleDtos = roles.stream().map(role -> {
                        var roleDto = new RoleDto(role.getId(), role.getName(), role.getCode(), role.getDescription());
                        return roleDto;
                }).toList();

                EmployeeDto employeeDto = EmployeeDto.mapToEntity(employee, roleDtos);

                return new ApiResponse<>(HttpStatusCode.OK, employeeDto);
        }

        @Override
        public ApiResponse<PageResult<List<EmployeeDto>>> getEmployees(int page, int size) {

                Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

                var employees = employeeDao.findAllByRecordStatus(1, pageable);

                List<EmployeeDto> employeeDtos = new ArrayList<>();

                for (Employee employee : employees.getContent()) {
                        List<Role> roles = roleDao.findByEmployeesIdAndRecordStatus(employee.getId(), 1);

                        List<RoleDto> roleDtos = roles.stream().map(role -> {
                                var roleDto = new RoleDto(role.getId(), role.getName(), role.getCode(),
                                                role.getDescription());
                                return roleDto;
                        }).toList();

                        EmployeeDto employeeDto = EmployeeDto.mapToEntity(employee, roleDtos);
                        employeeDtos.add(employeeDto);
                }

                Map<String, List<EmployeeDto>> employeeList = new HashMap<>();
                employeeList.put("employees", employeeDtos);

                PageResult<List<EmployeeDto>> pageResult = new PageResult<List<EmployeeDto>>(
                                employees.getTotalElements(),
                                employees.getTotalPages(), employees.getNumber(), employeeList);

                return new ApiResponse<>(HttpStatusCode.OK, pageResult);

        }

        @Override
        public ApiResponse<UUID> deleteEmployee(UUID id) {
                Employee employee = employeeDao.findByIdAndRecordStatus(id, 1)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee Id"));
                employee.setRecordStatus(0);
                Employee deletedEmployee = employeeDao.save(employee);
                return new ApiResponse<UUID>(HttpStatusCode.OK, "Deleted Employee Successfully.",
                                deletedEmployee.getId());
        }
}
