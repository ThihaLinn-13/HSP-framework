package com.hover.project.department.dao;

import com.hover.project.department.entity.Department;
import com.hover.project.position.entity.Position;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends JpaRepository<Department, UUID> {

    Page<Department> findAllByRecordStatus(int recordStatus, Pageable pageable);

    Optional<Department> findByIdAndRecordStatus(UUID id, int recordStatus);

}
