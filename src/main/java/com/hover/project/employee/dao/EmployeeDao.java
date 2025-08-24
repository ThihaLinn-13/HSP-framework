package com.hover.project.employee.dao;

import com.hover.project.employee.entity.Employee;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByUserNameOrEmail(String username, String email);

    Optional<Employee> findByUserName(String userName);
}
