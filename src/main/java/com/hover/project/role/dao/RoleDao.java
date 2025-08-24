package com.hover.project.role.dao;

import com.hover.project.department.entity.Department;
import com.hover.project.role.entity.Role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleDao extends JpaRepository<Role, UUID> {

    List<Role> findByEmployeesIdAndRecordStatus(UUID id, int recordStatus);

    Page<Role> findAllByRecordStatus(int recordStatus, Pageable pageable);

    Optional<Role> findByIdAndRecordStatus(UUID id, int recordStatus);

    List<Role> findAllByIdInAndRecordStatus(List<UUID> ids, int recordStatus);

}
