package com.hover.project.department.impl;

import com.hover.project.department.dao.DepartmentDao;
import com.hover.project.department.dto.AddDepartmentRequest;
import com.hover.project.department.dto.DepartmentDto;
import com.hover.project.department.dto.UpdateDepartmentRequest;
import com.hover.project.department.entity.Department;
import com.hover.project.department.service.DepartmentService;
import com.hover.project.role.dto.RoleDto;
import com.hover.project.util.type.ApiResponse;
import com.hover.project.util.type.HttpStatusCode;
import com.hover.project.util.type.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DepartmentImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public ApiResponse<UUID> addDepartment(AddDepartmentRequest addDepartmentRequest) {

        Department department = addDepartmentRequest.mapToEntity();

        Department newDepartment = departmentDao.save(department);

        return new ApiResponse<>(HttpStatusCode.CREATED, "Successfully created department.", newDepartment.getId());
    }

    @Override
    public ApiResponse<PageResult<List<DepartmentDto>>> getDepartments(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        var roles = departmentDao.findAllByRecordStatus(1, pageable);

        Map<String, List<DepartmentDto>> departmentList = new HashMap<>();
        departmentList.put("roles", roles.getContent().stream().map(DepartmentDto::mapToDto).toList());

        PageResult<List<DepartmentDto>> pageResult = new PageResult<List<DepartmentDto>>(
                roles.getTotalElements(),
                roles.getTotalPages(),
                roles.getNumber(),
                departmentList);

        return new ApiResponse<>(HttpStatusCode.OK, pageResult);
    }

    @Override
    public ApiResponse<DepartmentDto> getDepartmentById(UUID id) {
        var department = departmentDao.findByIdAndRecordStatus(id, 1);

        if (department.isEmpty()) {
            return new ApiResponse<>(HttpStatusCode.NOT_FOUND, "Department not found.", null);
        }
        DepartmentDto departmentDto = DepartmentDto.mapToDto(department.get());

        return new ApiResponse<>(HttpStatusCode.OK, departmentDto);

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
