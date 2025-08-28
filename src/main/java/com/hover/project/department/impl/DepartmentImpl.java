package com.hover.project.department.impl;

import com.hover.project.department.dao.DepartmentDao;
import com.hover.project.department.entity.Department;
import com.hover.project.department.mapper.DepartmentMapper;
import com.hover.project.department.request.AddDepartmentRequest;
import com.hover.project.department.request.UpdateDepartmentRequest;
import com.hover.project.department.response.DepartmentResponse;
import com.hover.project.department.service.DepartmentService;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.HttpStatusCode;
import com.hover.project.util.type.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DepartmentImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public ApiResponse<UUID> addDepartment(@RequestBody AddDepartmentRequest addDepartmentRequest) {

        Department department = departmentMapper.mapToEntityFromAddDepartmentRequest(addDepartmentRequest);

        Department newDepartment = departmentDao.save(department);

        return new ApiResponse<>(HttpStatusCode.CREATED, "Successfully created department.", newDepartment.getId());
    }

    @Override
    public ApiResponse<PageResult<List<DepartmentResponse>>> getDepartments(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        var roles = departmentDao.findAllByRecordStatus(1, pageable);

        Map<String, List<DepartmentResponse>> departmentList = new HashMap<>();
        departmentList.put("departments", roles.getContent().stream().map(departmentMapper::mapToDto).toList());

        PageResult<List<DepartmentResponse>> pageResult = new PageResult<List<DepartmentResponse>>(
                roles.getTotalElements(),
                roles.getTotalPages(),
                roles.getNumber(),
                departmentList);

        return new ApiResponse<>(HttpStatusCode.OK, pageResult);
    }

    @Override
    public ApiResponse<DepartmentResponse> getDepartmentById(UUID id) {
        var department = departmentDao.findByIdAndRecordStatus(id, 1);

        if (department.isEmpty()) {
            return new ApiResponse<>(HttpStatusCode.NOT_FOUND, "Department not found.", null);
        }
        DepartmentResponse DepartmentResponse = departmentMapper.mapToDto(department.get());

        return new ApiResponse<>(HttpStatusCode.OK, DepartmentResponse);

    }

    @Override
    public ApiResponse<UUID> updateDepartment(UUID id, UpdateDepartmentRequest updateDepartmentRequest) {

        var department = departmentDao.findByIdAndRecordStatus(id, 1);

        if (department.isEmpty()) {
            return new ApiResponse<>(HttpStatusCode.NOT_FOUND, "Department not found.", null);
        }

        Department existingDepartment = department.get();

        if (updateDepartmentRequest.code() != null)
            existingDepartment.setCode(updateDepartmentRequest.code());

        if (updateDepartmentRequest.name() != null)
            existingDepartment.setName(updateDepartmentRequest.name());

        if (updateDepartmentRequest.description() != null)
            existingDepartment.setDescription(updateDepartmentRequest.description());

        Department updatedDepartment = departmentDao.save(existingDepartment);

        return new ApiResponse<>(HttpStatusCode.OK, "Successfully updated department.", updatedDepartment.getId());
    }

    @Override
    public ApiResponse<UUID> deleteDepartment(UUID id) {

        var department = departmentDao.findByIdAndRecordStatus(id, 1);

        if (department.isEmpty()) {
            return new ApiResponse<>(HttpStatusCode.NOT_FOUND, "Department not found.", null);
        }

        department.get().setRecordStatus(4);
        var deletedDepartment = departmentDao.save(department.get());

        return new ApiResponse<>(HttpStatusCode.OK, "Successfully deleted department.", deletedDepartment.getId());

    }
}
