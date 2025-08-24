package com.hover.project.employee.dao;

import com.hover.project.employee.entity.Employee;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByUserNameOrEmailAndRecordStatus(String username, String email, int recordStatus);

    Optional<Employee> findByUserNameAndRecordStatus(String userName, int recordStatus);

    Optional<Employee> findByIdAndRecordStatus(UUID id, int recordStatus);

    Page<Employee> findAllByRecordStatus(int recordStatus, Pageable pageable);
}
