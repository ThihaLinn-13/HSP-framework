package com.hover.project.employee.impl;

import com.hover.project.department.dao.DepartmentDao;
import com.hover.project.employee.dao.EmployeeDao;
import com.hover.project.employee.entity.Employee;
import com.hover.project.employee.mapper.EmployeeMapper;
import com.hover.project.employee.request.CreateEmployeeRequest;
import com.hover.project.employee.request.UpdateEmployeeRequest;
import com.hover.project.employee.response.EmployeeResponse;
import com.hover.project.employee.service.EmployeeService;
import com.hover.project.position.dao.PositionDao;
import com.hover.project.role.dao.RoleDao;
import com.hover.project.role.maper.RoleMapper;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.HttpStatusCode;
import com.hover.project.util.type.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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
        private EmployeeMapper employeeMapper;

        @Autowired
        private RoleMapper roleMapper;

        @Override
        public ApiResponse<UUID> createEmployee(CreateEmployeeRequest createEmployeeRequest) {

                Employee employee = employeeMapper.mapToEntityFromCreateEmployeeRequest(createEmployeeRequest);

                Employee newEmployee = employeeDao.save(employee);

                return new ApiResponse<UUID>(HttpStatusCode.CREATED, "Created Employee Successfully.",
                                newEmployee.getId());
        }

        @Override
        public ApiResponse<EmployeeResponse> getEmployeeById(UUID id) {

                Employee employee = employeeDao.findByIdAndRecordStatus(id, 1)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee Id"));

                EmployeeResponse employeeDto = employeeMapper.mapToEmployeeResponse(employee);

                return new ApiResponse<>(HttpStatusCode.OK, employeeDto);
        }

        @Override
        public ApiResponse<PageResult<List<EmployeeResponse>>> getEmployees(int page, int size) {

                Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
                var employeePage = employeeDao.findAllByRecordStatus(1, pageable);

                List<EmployeeResponse> employeeResponses = employeePage.getContent().stream()
                                .map(employeeMapper::mapToEmployeeResponse)
                                .toList();

                PageResult<List<EmployeeResponse>> pageResult = new PageResult<>(
                                employeePage.getTotalElements(),
                                employeePage.getTotalPages(),
                                employeePage.getNumber(),
                                Map.of("employees", employeeResponses));

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

        @Override
        public ApiResponse<UUID> updateEmployee(UpdateEmployeeRequest updateEmployeeRequest) {
                employeeDao.findByIdAndRecordStatus(updateEmployeeRequest.id(), 1)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee Id"));

                Employee UpdatedEmployee = employeeMapper.mapToEntityFromUpdateEmployeeRequest(updateEmployeeRequest);

                UpdatedEmployee = employeeDao.save(UpdatedEmployee);

                return new ApiResponse<UUID>(HttpStatusCode.OK, "Updated Employee Successfully.",
                                UpdatedEmployee.getId());
        }
}
